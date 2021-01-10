package com.isfong.cnm.product.domain.events;

import com.isfong.cnm.product.domain.models.entities.Product;
import com.isfong.cnm.product.sdk.events.ProductDomainEvent;
import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

public class ProductDomainEventPublisher extends AbstractAggregateDomainEventPublisher< Product, ProductDomainEvent > {
    public ProductDomainEventPublisher( DomainEventPublisher eventPublisher ) {
        super( eventPublisher, Product.class, Product::getId );
    }
}
