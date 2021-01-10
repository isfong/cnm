package com.isfong.cnm.order.sdk.events;

import com.isfong.cnm.order.sdk.representations.OrderItemRepresentation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor( access = PRIVATE )
public class OrderApprovedEvent extends OrderDomainEvent {
    private List< OrderItemRepresentation > items;

    public OrderApprovedEvent( List< OrderItemRepresentation > items ) {
        this.items = items;
    }
}
