package com.isfong.cnm.product.sdk.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class ProductNameChangedEvent extends ProductDomainEvent {
    private String originalName;
    private String newName;

    public ProductNameChangedEvent( String originalName, String newName ) {
        this.originalName = originalName;
        this.newName = newName;
    }
}
