package com.isfong.cnm.order.sdk.commands;

import com.isfong.cnm.order.sdk.representations.OrderItemRepresentation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class ValidationOrderProductsSagaCommand extends OrderSagaCommand {
    private List< OrderItemRepresentation > items;

    public ValidationOrderProductsSagaCommand( String orderId, List< OrderItemRepresentation > items ) {
        super( orderId );
        this.items = items;
    }
}
