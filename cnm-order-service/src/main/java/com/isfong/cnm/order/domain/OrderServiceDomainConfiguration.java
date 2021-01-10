package com.isfong.cnm.order.domain;

import com.isfong.cnm.order.domain.events.OrderDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.spring.boot.annotation.EnableTramEventsPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableTramEventsPublisher
public class OrderServiceDomainConfiguration {

    @Bean
    public OrderDomainEventPublisher orderDomainEventPublisher( DomainEventPublisher domainEventPublisher ) {
        return new OrderDomainEventPublisher( domainEventPublisher );
    }
}
