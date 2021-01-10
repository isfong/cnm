package com.isfong.cnm.order.application.handlers;

import com.isfong.cnm.order.application.services.OrderApplicationService;
import com.isfong.cnm.order.sdk.events.OrderDomainEvent;
import com.isfong.cnm.order.sdk.events.OrderProductInventoryChangeFailedEvent;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceDomainEventHandlers {
    private final OrderApplicationService orderApplicationService;

    public OrderServiceDomainEventHandlers( OrderApplicationService orderApplicationService ) {
        this.orderApplicationService = orderApplicationService;
    }

    public DomainEventHandlers domainEventHandlers( ) {
        return DomainEventHandlersBuilder
                .forAggregateType( OrderDomainEvent.AggregateType )
                .onEvent( OrderProductInventoryChangeFailedEvent.class, this::inventoryChangeFailedHandler )
                .build( );
    }

    private void inventoryChangeFailedHandler( DomainEventEnvelope< OrderProductInventoryChangeFailedEvent > envelope ) {
        this.orderApplicationService.rejectOrder( envelope.getAggregateId( ) );
    }
}
