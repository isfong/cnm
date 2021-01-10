package com.isfong.cnm.order.sdk.commands;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class ApproveOrderSagaCommand extends OrderSagaCommand {
    public ApproveOrderSagaCommand( String orderId ) {
        super( orderId );
    }
}
