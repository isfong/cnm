package com.isfong.cnm.inventory.application;

import com.isfong.cnm.inventory.application.handlers.InventoryServiceDomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.spring.boot.annotation.EnableTramEventSubscriber;
import io.eventuate.tram.spring.boot.annotation.EnableTramNoopDuplicateMessageDetector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableTramEventSubscriber
@EnableTramNoopDuplicateMessageDetector
public class InventoryServiceApplicationConfiguration {

    @Bean
    public DomainEventDispatcher domainEventDispatcher( DomainEventDispatcherFactory factory,
                                                        InventoryServiceDomainEventHandlers handlers ) {
        return factory.make( handlers.getClass( ).getSimpleName( ), handlers.domainEventHandlers( ) );
    }
}
