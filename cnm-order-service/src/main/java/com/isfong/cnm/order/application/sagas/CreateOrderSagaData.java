package com.isfong.cnm.order.application.sagas;

import com.isfong.cnm.order.sdk.commands.ApproveOrderSagaCommand;
import com.isfong.cnm.order.sdk.commands.RejectOrderSagaCommand;
import com.isfong.cnm.order.sdk.commands.ValidationOrderProductsSagaCommand;
import com.isfong.cnm.order.sdk.representations.OrderItemRepresentation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor( access = PRIVATE )
public class CreateOrderSagaData {
    private String orderId;
    private List< OrderItemRepresentation > items;

    public CreateOrderSagaData( String orderId, List< OrderItemRepresentation > items ) {
        this.orderId = orderId;
        this.items = items;
    }

    public RejectOrderSagaCommand makeRejectOrderSagaCommand( ) {
        return new RejectOrderSagaCommand( orderId );
    }

    public ValidationOrderProductsSagaCommand makeValidationOrderProductsSagaCommand( ) {
        return new ValidationOrderProductsSagaCommand( this.orderId, this.items );
    }

    public ApproveOrderSagaCommand makeApproveOrderSagaCommand( ) {
        return new ApproveOrderSagaCommand( this.orderId );
    }
}
