package com.isfong.cnm.product.application.handlers;

import com.isfong.cnm.order.sdk.commands.OrderSagaCommand;
import com.isfong.cnm.order.sdk.commands.ValidationOrderProductsSagaCommand;
import com.isfong.cnm.product.application.services.ProductApplicationService;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@Component
public class ProductServiceSagaCommandHandlers {
    private final ProductApplicationService productApplicationService;

    public ProductServiceSagaCommandHandlers( ProductApplicationService productApplicationService ) {
        this.productApplicationService = productApplicationService;
    }

    public CommandHandlers commandHandlers( ) {
        return SagaCommandHandlersBuilder
                .fromChannel( OrderSagaCommand.SagaCommandChannel.product )
                .onMessage( ValidationOrderProductsSagaCommand.class, this::validationOrderProductsSagaCommandHandler )
                .build( );
    }

    private Message validationOrderProductsSagaCommandHandler( CommandMessage< ValidationOrderProductsSagaCommand > message ) {
        try {
            this.productApplicationService.validationOrderProducts( message.getCommand( ).getItems( ) );
            return withSuccess( );
        } catch ( Exception e ) {
            return withFailure( );
        }
    }
}
