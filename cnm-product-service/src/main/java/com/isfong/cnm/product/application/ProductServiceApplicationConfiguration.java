package com.isfong.cnm.product.application;

import com.isfong.cnm.product.application.handlers.ProductServiceDomainEventHandlers;
import com.isfong.cnm.product.application.handlers.ProductServiceSagaCommandHandlers;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.spring.boot.annotation.EnableSagaParticipant;
import io.eventuate.tram.spring.boot.annotation.EnableTramEventSubscriber;
import io.eventuate.tram.spring.boot.annotation.EnableTramNoopDuplicateMessageDetector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSagaParticipant
@EnableTramEventSubscriber
@EnableTramNoopDuplicateMessageDetector
public class ProductServiceApplicationConfiguration {

    @Bean
    public DomainEventDispatcher domainEventDispatcher( DomainEventDispatcherFactory factory,
                                                        ProductServiceDomainEventHandlers handlers ) {
        return factory.make( handlers.getClass( ).getSimpleName( ), handlers.domainEventHandlers( ) );
    }

    @Bean
    public SagaCommandDispatcher sagaCommandDispatcher( SagaCommandDispatcherFactory factory,
                                                        ProductServiceSagaCommandHandlers handlers ) {
        return factory.make( handlers.getClass( ).getSimpleName( ), handlers.commandHandlers( ) );
    }
}
