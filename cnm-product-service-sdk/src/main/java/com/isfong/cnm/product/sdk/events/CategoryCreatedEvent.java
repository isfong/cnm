package com.isfong.cnm.product.sdk.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class CategoryCreatedEvent extends CategoryDomainEvent {
    private String name;
    private String description;

    public CategoryCreatedEvent( String name, String description ) {
        this.name = name;
        this.description = description;
    }
}
