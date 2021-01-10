package com.isfong.cnm.order.application.sagas;

import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderSaga implements SimpleSaga< CreateOrderSagaData > {
    @Override
    public SagaDefinition< CreateOrderSagaData > getSagaDefinition( ) {
        return this
                // 事务补偿
                .step( )
                .withCompensation( SagaServiceProxies.OrderServiceProxy.stepOfReject, CreateOrderSagaData::makeRejectOrderSagaCommand )
                // 校验库存
                .step( )
                .invokeParticipant( SagaServiceProxies.ProductServiceProxy.stepOfValidationOrderProducts, CreateOrderSagaData::makeValidationOrderProductsSagaCommand )
                // 批准订单
                .step( )
                .invokeParticipant( SagaServiceProxies.OrderServiceProxy.stepOfApprove, CreateOrderSagaData::makeApproveOrderSagaCommand )

                .build( );
    }
}
