package com.isfong.cnm.product.domain.models.entities;

import com.isfong.cnm.product.domain.models.ProductInventoryInsufficientException;
import com.isfong.cnm.product.sdk.events.ProductCreatedEvent;
import com.isfong.cnm.product.sdk.events.ProductDomainEvent;
import com.isfong.cnm.product.sdk.events.ProductNameChangedEvent;
import com.isfong.cnm.product.sdk.representations.ProductRepresentation;
import com.isfong.cnm.shared.model.utils.UUIDGenerator;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter( AccessLevel.PRIVATE )
@Builder( access = AccessLevel.PRIVATE )
@NoArgsConstructor( access = AccessLevel.PRIVATE )
@AllArgsConstructor( access = AccessLevel.PRIVATE )
public class Product {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long createdAt;
    private int inventory;
    private String categoryId;

    public static ResultWithDomainEvents< Product, ProductDomainEvent > create( String name, String description, BigDecimal price, String categoryId ) {
        Product product = Product.builder( )
                .id( UUIDGenerator.newUUID( ) )
                .name( name )
                .description( description )
                .price( price )
                .categoryId( categoryId )
                .createdAt( System.currentTimeMillis( ) )
                .inventory( 0 )
                .build( );
        return new ResultWithDomainEvents<>( product, new ProductCreatedEvent( name, description, price, categoryId ) );
    }

    public ProductRepresentation toRepresentation( ) {
        return new ProductRepresentation( id, name, description, price, createdAt, inventory, categoryId );
    }

    public ResultWithDomainEvents< Product, ProductDomainEvent > changeName( String newName ) {
        ProductNameChangedEvent productNameChangedEvent = new ProductNameChangedEvent( name, newName );
        this.name = newName;
        return new ResultWithDomainEvents<>( this, productNameChangedEvent );
    }

    public void validationInventory( int orderInventory ) {
        if ( orderInventory > this.inventory ) {
            throw new ProductInventoryInsufficientException( );
        }
    }

    public ResultWithDomainEvents< Product, ProductDomainEvent > changeInventory( int remains ) {
        this.inventory = remains;
        return new ResultWithDomainEvents<>( this );
    }
}
