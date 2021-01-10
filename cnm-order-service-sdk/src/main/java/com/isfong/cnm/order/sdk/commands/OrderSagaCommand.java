package com.isfong.cnm.order.sdk.commands;

import io.eventuate.tram.commands.common.Command;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class OrderSagaCommand implements Command {
    public interface SagaCommandChannel {
        String order = "com.example.order";
        String product = "com.example.product";
    }

    private String orderId;

    public OrderSagaCommand( String orderId ) {
        this.orderId = orderId;
    }
}
