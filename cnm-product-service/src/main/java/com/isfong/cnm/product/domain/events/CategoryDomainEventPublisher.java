package com.isfong.cnm.product.domain.events;

import com.isfong.cnm.product.domain.models.entities.Category;
import com.isfong.cnm.product.sdk.events.CategoryDomainEvent;
import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;


public class CategoryDomainEventPublisher extends AbstractAggregateDomainEventPublisher< Category, CategoryDomainEvent > {

    public CategoryDomainEventPublisher( DomainEventPublisher eventPublisher ) {
        super( eventPublisher, Category.class, Category::getId );
    }
}
