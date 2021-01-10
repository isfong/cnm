package com.isfong.cnm.order.domain.events;

import com.isfong.cnm.order.domain.models.entities.Order;
import com.isfong.cnm.order.sdk.events.OrderDomainEvent;
import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

public class OrderDomainEventPublisher extends AbstractAggregateDomainEventPublisher< Order, OrderDomainEvent > {
    public OrderDomainEventPublisher( DomainEventPublisher eventPublisher ) {
        super( eventPublisher, Order.class, Order::getId );
    }
}
