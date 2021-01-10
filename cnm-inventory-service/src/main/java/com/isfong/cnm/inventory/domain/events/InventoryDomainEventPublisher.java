package com.isfong.cnm.inventory.domain.events;

import com.isfong.cnm.inventory.domain.models.entities.Inventory;
import com.isfong.cnm.inventory.sdk.events.InventoryDomainEvent;
import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InventoryDomainEventPublisher extends AbstractAggregateDomainEventPublisher< Inventory, InventoryDomainEvent > {
    protected InventoryDomainEventPublisher( DomainEventPublisher eventPublisher ) {
        super( eventPublisher, Inventory.class, Inventory::getId );
    }
}
