package com.isfong.cnm.product.domain.models.entities;

import com.isfong.cnm.product.sdk.events.CategoryCreatedEvent;
import com.isfong.cnm.product.sdk.events.CategoryDomainEvent;
import com.isfong.cnm.product.sdk.representations.CategoryRepresentation;
import com.isfong.cnm.shared.model.utils.UUIDGenerator;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter( AccessLevel.PRIVATE )
@Builder( access = AccessLevel.PRIVATE )
public class Category {
    private String id;
    private String name;
    private String description;
    private Long createdAt;

    public static ResultWithDomainEvents< Category, CategoryDomainEvent > create( String name, String description ) {
        Category category = Category.builder( )
                .id( UUIDGenerator.newUUID( ) )
                .name( name )
                .description( description )
                .createdAt( System.currentTimeMillis( ) )
                .build( );
        return new ResultWithDomainEvents<>( category, new CategoryCreatedEvent( name, description ) );
    }

    public CategoryRepresentation toRepresentation( ) {
        return new CategoryRepresentation( id, name, description, createdAt );
    }
}
