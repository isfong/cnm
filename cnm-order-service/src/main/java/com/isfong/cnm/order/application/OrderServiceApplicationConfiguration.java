package com.isfong.cnm.order.application;

import com.isfong.cnm.order.application.handlers.OrderServiceDomainEventHandlers;
import com.isfong.cnm.order.application.handlers.OrderServiceSagaCommandHandlers;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.spring.boot.annotation.EnableSagaOrchestrator;
import io.eventuate.tram.spring.boot.annotation.EnableSagaParticipant;
import io.eventuate.tram.spring.boot.annotation.EnableTramEventSubscriber;
import io.eventuate.tram.spring.boot.annotation.EnableTramNoopDuplicateMessageDetector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSagaParticipant
@EnableSagaOrchestrator
@EnableTramEventSubscriber
@EnableTramNoopDuplicateMessageDetector
public class OrderServiceApplicationConfiguration {

    @Bean
    public SagaCommandDispatcher commandDispatcher( SagaCommandDispatcherFactory factory,
                                                    OrderServiceSagaCommandHandlers handlers ) {
        return factory.make( handlers.getClass( ).getSimpleName( ), handlers.sagaCommandHeaders( ) );
    }

    @Bean
    public DomainEventDispatcher domainEventDispatcher( DomainEventDispatcherFactory factory,
                                                        OrderServiceDomainEventHandlers eventHandlers ) {
        return factory.make( eventHandlers.getClass( ).getSimpleName( ), eventHandlers.domainEventHandlers( ) );
    }
}
