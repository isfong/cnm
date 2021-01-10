package com.isfong.cnm.product.sdk.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class ProductCreatedEvent extends ProductDomainEvent {
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryId;

    public ProductCreatedEvent( String name, String description, BigDecimal price, String categoryId ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }
}
