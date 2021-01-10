package com.isfong.cnm.product.application.services;

import com.isfong.cnm.product.domain.events.CategoryDomainEventPublisher;
import com.isfong.cnm.product.domain.models.entities.Category;
import com.isfong.cnm.product.domain.repositories.CategoryRepository;
import com.isfong.cnm.product.sdk.commands.CreateCategoryCommand;
import com.isfong.cnm.product.sdk.events.CategoryDomainEvent;
import com.isfong.cnm.product.sdk.representations.CategoryRepresentation;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryApplicationService {
    private final CategoryRepository categoryRepository;
    private final CategoryDomainEventPublisher categoryDomainEventPublisher;

    public CategoryApplicationService( CategoryRepository categoryRepository, CategoryDomainEventPublisher categoryDomainEventPublisher ) {
        this.categoryRepository = categoryRepository;
        this.categoryDomainEventPublisher = categoryDomainEventPublisher;
    }

    public String createCategory( CreateCategoryCommand command ) throws Exception {
        ResultWithDomainEvents< Category, CategoryDomainEvent > resultWithDomainEvents = Category.create( command.getName( ), command.getDescription( ) );
        Category category = this.categoryRepository.save( resultWithDomainEvents.result );
        this.categoryDomainEventPublisher.publish( category, resultWithDomainEvents.events );
        return category.getId( );
    }

    public Optional< CategoryRepresentation > getCategory( String id ) {
        return this.categoryRepository.findById( id ).map( Category::toRepresentation );
    }
}
