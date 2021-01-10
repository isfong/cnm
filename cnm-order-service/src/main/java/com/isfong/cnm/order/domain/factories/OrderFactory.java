package com.isfong.cnm.order.domain.factories;

import com.isfong.cnm.order.application.services.IdGeneratorDomainService;
import com.isfong.cnm.order.domain.models.OrderItem;
import com.isfong.cnm.order.domain.models.entities.Order;
import com.isfong.cnm.order.sdk.events.OrderDomainEvent;
import com.isfong.cnm.order.sdk.models.Address;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderFactory {
    private final IdGeneratorDomainService idGeneratorDomainService;


    public OrderFactory( IdGeneratorDomainService idGeneratorDomainService ) {
        this.idGeneratorDomainService = idGeneratorDomainService;
    }

    public ResultWithDomainEvents< Order, OrderDomainEvent > make( List< OrderItem > items, Address address ) {
        return Order.create( idGeneratorDomainService.uuid( ), items, address );
    }
}
