package com.isfong.cnm.order.application.sagas;

import com.isfong.cnm.order.sdk.commands.ApproveOrderSagaCommand;
import com.isfong.cnm.order.sdk.commands.OrderSagaCommand;
import com.isfong.cnm.order.sdk.commands.RejectOrderSagaCommand;
import com.isfong.cnm.order.sdk.commands.ValidationOrderProductsSagaCommand;
import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

public interface SagaServiceProxies {

    interface OrderServiceProxy {
        CommandEndpoint< RejectOrderSagaCommand > stepOfReject = CommandEndpointBuilder
                .forCommand( RejectOrderSagaCommand.class )
                .withChannel( OrderSagaCommand.SagaCommandChannel.order )
                .withReply( Success.class )
                .build( );
        CommandEndpoint< ApproveOrderSagaCommand > stepOfApprove = CommandEndpointBuilder
                .forCommand( ApproveOrderSagaCommand.class )
                .withChannel( OrderSagaCommand.SagaCommandChannel.order )
                .withReply( Success.class )
                .build( );
    }

    interface ProductServiceProxy {
        CommandEndpoint< ValidationOrderProductsSagaCommand > stepOfValidationOrderProducts = CommandEndpointBuilder
                .forCommand( ValidationOrderProductsSagaCommand.class )
                .withChannel( OrderSagaCommand.SagaCommandChannel.product )
                .withReply( Success.class )
                .build( );
    }
}
