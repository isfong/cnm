package com.isfong.cnm.order.sdk.events;

import io.eventuate.tram.events.common.DomainEvent;

public abstract class OrderDomainEvent implements DomainEvent {
    public static final String AggregateType = "com.isfong.cnm.order.domain.models.entities.Order";
}
