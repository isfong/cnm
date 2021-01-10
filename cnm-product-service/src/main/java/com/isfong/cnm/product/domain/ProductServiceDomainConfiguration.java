package com.isfong.cnm.product.domain;

import com.isfong.cnm.product.domain.events.CategoryDomainEventPublisher;
import com.isfong.cnm.product.domain.events.ProductDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.spring.boot.annotation.EnableTramEventsPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableTramEventsPublisher
public class ProductServiceDomainConfiguration {

    @Bean
    public ProductDomainEventPublisher productDomainEventPublisher( DomainEventPublisher domainEventPublisher ) {
        return new ProductDomainEventPublisher( domainEventPublisher );
    }

    @Bean
    public CategoryDomainEventPublisher categoryDomainEventPublisher( DomainEventPublisher domainEventPublisher ) {
        return new CategoryDomainEventPublisher( domainEventPublisher );
    }
}
