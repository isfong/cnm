package com.isfong.cnm.order.application.services;

import com.google.common.collect.ImmutableBiMap;
import com.isfong.cnm.order.application.sagas.CreateOrderSaga;
import com.isfong.cnm.order.application.sagas.CreateOrderSagaData;
import com.isfong.cnm.order.domain.events.OrderDomainEventPublisher;
import com.isfong.cnm.order.domain.factories.OrderFactory;
import com.isfong.cnm.order.domain.models.OrderItem;
import com.isfong.cnm.order.domain.models.OrderNotFoundException;
import com.isfong.cnm.order.domain.models.entities.Order;
import com.isfong.cnm.order.domain.repositories.OrderRepository;
import com.isfong.cnm.order.sdk.commands.CreateOrderCommand;
import com.isfong.cnm.order.sdk.events.OrderDomainEvent;
import com.isfong.cnm.order.sdk.representations.OrderItemRepresentation;
import com.isfong.cnm.order.sdk.representations.OrderRepresentation;
import com.isfong.cnm.shared.model.representations.PagedRepresentations;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderApplicationService {
    private final OrderFactory orderFactory;
    private final OrderRepository orderRepository;
    private final CreateOrderSaga createOrderSaga;
    private final SagaInstanceFactory sagaInstanceFactory;
    private final OrderDomainEventPublisher orderDomainEventPublisher;

    public OrderApplicationService( OrderFactory orderFactory, OrderRepository orderRepository, CreateOrderSaga createOrderSaga, SagaInstanceFactory sagaInstanceFactory, OrderDomainEventPublisher orderDomainEventPublisher ) {
        this.orderFactory = orderFactory;
        this.orderRepository = orderRepository;
        this.createOrderSaga = createOrderSaga;
        this.sagaInstanceFactory = sagaInstanceFactory;
        this.orderDomainEventPublisher = orderDomainEventPublisher;
    }

    @Transactional
    public String createOrder( CreateOrderCommand command ) {
        List< OrderItem > items = command.getItems( ).stream( )
                .map( itemCommand -> new OrderItem( itemCommand.getProductId( ), itemCommand.getCount( ), itemCommand.getUnitPrice( ) ) )
                .collect( Collectors.toList( ) );
        ResultWithDomainEvents< Order, OrderDomainEvent > made = this.orderFactory.make( items, command.getAddress( ) );
        Order saved = this.orderRepository.save( made.result );
        String orderId = saved.getId( );
        this.orderDomainEventPublisher.publish( made.result, made.events );
        List< OrderItemRepresentation > itemRepresentations = command.getItems( ).stream( ).map( item -> new OrderItemRepresentation( item.getProductId( ), item.getCount( ), item.getUnitPrice( ) ) )
                .collect( Collectors.toList( ) );
        this.sagaInstanceFactory.create( this.createOrderSaga, new CreateOrderSagaData( orderId, itemRepresentations ) );
        return orderId;
    }

    public void rejectOrder( String id ) {
        Order order = this.orderRepository.findById( id )
                .orElseThrow( ( ) -> new OrderNotFoundException( ImmutableBiMap.of( "id", id ) ) );
        ResultWithDomainEvents< Order, OrderDomainEvent > rejected = order.reject( );
        this.orderDomainEventPublisher.publish( rejected.result, rejected.events );
    }

    public void approveOrder( String id ) {
        Order order = this.orderRepository.findById( id )
                .orElseThrow( ( ) -> new OrderNotFoundException( ImmutableBiMap.of( "id", id ) ) );
        ResultWithDomainEvents< Order, OrderDomainEvent > approved = order.approve( );
        this.orderDomainEventPublisher.publish( approved.result, approved.events );
    }

    public Optional< OrderRepresentation > getOrder( String id ) {
        return this.orderRepository.findById( id ).map( Order::toRepresentation );
    }

    public PagedRepresentations< OrderRepresentation > getOrders( Pageable page ) {
        List< OrderRepresentation > representations = StreamSupport.stream( this.orderRepository.findAll( page ).spliterator( ), false )
                .map( Order::toRepresentation )
                .collect( Collectors.toList( ) );
        long count = this.orderRepository.count( );
        return PagedRepresentations.of( Long.valueOf( count ).intValue( ), page.getPageNumber( ), representations );
    }
}
