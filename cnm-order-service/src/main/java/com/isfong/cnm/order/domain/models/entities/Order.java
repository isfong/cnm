package com.isfong.cnm.order.domain.models.entities;

import com.isfong.cnm.order.domain.models.OrderItem;
import com.isfong.cnm.order.domain.models.OrderState;
import com.isfong.cnm.order.sdk.events.OrderApprovedEvent;
import com.isfong.cnm.order.sdk.events.OrderDomainEvent;
import com.isfong.cnm.order.sdk.events.OrderRejectedEvent;
import com.isfong.cnm.order.sdk.models.Address;
import com.isfong.cnm.order.sdk.representations.OrderItemRepresentation;
import com.isfong.cnm.order.sdk.representations.OrderRepresentation;
import com.isfong.cnm.shared.model.models.PersistableEntity;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;
import static javax.persistence.FetchType.EAGER;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder( access = PRIVATE )
@AllArgsConstructor( access = PRIVATE )
@NoArgsConstructor( access = PROTECTED )
public class Order extends PersistableEntity< String > {
    @Id
    private String id;
    @ElementCollection( fetch = EAGER )
    private List< OrderItem > items;
    private BigDecimal totalPrice;
    @Enumerated( EnumType.STRING )
    private OrderState state;
    @Embedded
    private Address address;

    public static ResultWithDomainEvents< Order, OrderDomainEvent > create( String id, List< OrderItem > items, Address address ) {
        Order order = Order.builder( )
                .id( id )
                .items( items )
                .totalPrice( calculateTotalPrice( items ) )
                .state( OrderState.APPROVAL_PENDING )
                .address( address )
                .build( );
        return new ResultWithDomainEvents<>( order );
    }

    private static BigDecimal calculateTotalPrice( List< OrderItem > items ) {
        return items.stream( )
                .map( OrderItem::totalPrice )
                .reduce( ZERO, BigDecimal::add );
    }

    public ResultWithDomainEvents< Order, OrderDomainEvent > reject( ) {
        this.state = OrderState.REJECTED;
        return new ResultWithDomainEvents<>( this, new OrderRejectedEvent( ) );
    }

    public ResultWithDomainEvents< Order, OrderDomainEvent > approve( ) {
        this.state = OrderState.APPROVED;
        List< OrderItemRepresentation > products = this.items.stream( ).map( OrderItem::toRepresentation ).collect( Collectors.toList( ) );
        return new ResultWithDomainEvents<>( this, new OrderApprovedEvent( products ) );
    }

    public OrderRepresentation toRepresentation( ) {
        List< OrderItemRepresentation > itemRepresentations = this.items.stream( ).map( OrderItem::toRepresentation ).collect( Collectors.toList( ) );
        return new OrderRepresentation( id, getCreatedAt( ), getUpdatedAt( ), getVersion( ), totalPrice, state.name( ), itemRepresentations, address );
    }
}
