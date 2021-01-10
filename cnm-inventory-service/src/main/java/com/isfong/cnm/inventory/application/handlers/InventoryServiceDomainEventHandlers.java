package com.isfong.cnm.inventory.application.handlers;

import com.isfong.cnm.inventory.application.services.InventoryApplicationService;
import com.isfong.cnm.order.sdk.events.OrderApprovedEvent;
import com.isfong.cnm.order.sdk.events.OrderDomainEvent;
import com.isfong.cnm.order.sdk.events.OrderProductInventoryChangeFailedEvent;
import com.isfong.cnm.product.sdk.events.ProductCreatedEvent;
import com.isfong.cnm.product.sdk.events.ProductDomainEvent;
import com.isfong.cnm.product.sdk.events.ProductNameChangedEvent;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class InventoryServiceDomainEventHandlers {
    private final DomainEventPublisher domainEventPublisher;
    private final InventoryApplicationService inventoryApplicationService;

    public InventoryServiceDomainEventHandlers( DomainEventPublisher domainEventPublisher, InventoryApplicationService inventoryApplicationService ) {
        this.domainEventPublisher = domainEventPublisher;
        this.inventoryApplicationService = inventoryApplicationService;
    }

    public DomainEventHandlers domainEventHandlers( ) {
        return DomainEventHandlersBuilder

                .forAggregateType( ProductDomainEvent.AggregateType )
                .onEvent( ProductCreatedEvent.class, this::productCreatedEventHandler )
                .onEvent( ProductNameChangedEvent.class, this::productNameChangedEventHandler )

                .andForAggregateType( OrderDomainEvent.AggregateType )
                .onEvent( OrderApprovedEvent.class, this::orderApprovedEventHandler )

                .build( );
    }

    private void orderApprovedEventHandler( DomainEventEnvelope< OrderApprovedEvent > envelope ) {
        OrderApprovedEvent event = envelope.getEvent( );
        try {
            this.inventoryApplicationService.decreaseInventory( event.getItems( ) );
        } catch ( Exception e ) {
            e.printStackTrace( );
            this.domainEventPublisher.publish( envelope.getAggregateType( ),
                    envelope.getAggregateId( ),
                    Collections.singletonList( new OrderProductInventoryChangeFailedEvent( ) ) );
        }
    }

    private void productCreatedEventHandler( DomainEventEnvelope< ProductCreatedEvent > envelope ) {
        String productId = envelope.getAggregateId( );
        this.inventoryApplicationService.createInventory( productId, envelope.getEvent( ).getName( ) );
    }

    private void productNameChangedEventHandler( DomainEventEnvelope< ProductNameChangedEvent > envelope ) {
        String productId = envelope.getAggregateId( );
        this.inventoryApplicationService.changeProductName( productId, envelope.getEvent( ).getNewName( ) );
    }
}
