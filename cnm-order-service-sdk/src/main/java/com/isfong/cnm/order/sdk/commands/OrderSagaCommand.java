package com.isfong.cnm.order.sdk.commands;

import io.eventuate.tram.commands.common.Command;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class OrderSagaCommand implements Command {
    public interface SagaCommandChannel {
        String order = "com.isfong.cnm.order";
        String product = "com.isfong.cnm.product";
    }

    private String orderId;

    public OrderSagaCommand( String orderId ) {
        this.orderId = orderId;
    }
}
