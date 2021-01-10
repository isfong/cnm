package com.isfong.cnm.product.application.handlers;

import com.isfong.cnm.inventory.sdk.events.InventoryChangedEvent;
import com.isfong.cnm.inventory.sdk.events.InventoryDomainEvent;
import com.isfong.cnm.product.application.services.ProductApplicationService;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceDomainEventHandlers {
    private final ProductApplicationService productApplicationService;

    public ProductServiceDomainEventHandlers( ProductApplicationService productApplicationService ) {
        this.productApplicationService = productApplicationService;
    }

    public DomainEventHandlers domainEventHandlers( ) {
        return DomainEventHandlersBuilder
                .forAggregateType( InventoryDomainEvent.aggregateType )
                .onEvent( InventoryChangedEvent.class, this::inventoryChangedEventHandler )
                .build( );
    }

    private void inventoryChangedEventHandler( DomainEventEnvelope< InventoryChangedEvent > envelope ) {
        InventoryChangedEvent event = envelope.getEvent( );
        this.productApplicationService.changeProductInventory( event.getProductId( ), event.getRemains( ) );
    }

}
