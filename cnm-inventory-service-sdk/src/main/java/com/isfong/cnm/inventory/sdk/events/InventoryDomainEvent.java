package com.isfong.cnm.inventory.sdk.events;

import io.eventuate.tram.events.common.DomainEvent;

public abstract class InventoryDomainEvent implements DomainEvent {
    public static final String aggregateType = "com.example.inventory.domain.models.entities.Inventory";
}
