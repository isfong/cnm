package com.isfong.cnm.order.application.handlers;

import com.isfong.cnm.order.application.services.OrderApplicationService;
import com.isfong.cnm.order.sdk.commands.ApproveOrderSagaCommand;
import com.isfong.cnm.order.sdk.commands.OrderSagaCommand;
import com.isfong.cnm.order.sdk.commands.RejectOrderSagaCommand;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@Component
public class OrderServiceSagaCommandHandlers {
    private final OrderApplicationService orderApplicationService;

    public OrderServiceSagaCommandHandlers( OrderApplicationService orderApplicationService ) {
        this.orderApplicationService = orderApplicationService;
    }

    public CommandHandlers sagaCommandHeaders( ) {
        return SagaCommandHandlersBuilder
                .fromChannel( OrderSagaCommand.SagaCommandChannel.order )
                .onMessage( RejectOrderSagaCommand.class, this::rejectOrderSagaCommandHeader )
                .onMessage( ApproveOrderSagaCommand.class, this::approveOrderSagaCommandHeader )
                .build( );
    }

    private Message approveOrderSagaCommandHeader( CommandMessage< ApproveOrderSagaCommand > message ) {
        try {
            this.orderApplicationService.approveOrder( message.getCommand( ).getOrderId( ) );
        } catch ( Exception e ) {
            return withFailure( );
        }
        return withSuccess( );
    }

    private Message rejectOrderSagaCommandHeader( CommandMessage< RejectOrderSagaCommand > message ) {
        try {
            this.orderApplicationService.rejectOrder( message.getCommand( ).getOrderId( ) );
        } catch ( Exception e ) {
            return withFailure( );
        }
        return withSuccess( );
    }
}
