package com.isfong.cnm.order.sdk.commands;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class RejectOrderSagaCommand extends OrderSagaCommand {
    public RejectOrderSagaCommand( String orderId ) {
        super( orderId );
    }
}
