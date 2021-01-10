package com.isfong.cnm.inventory.domain.models.entities;

import com.isfong.cnm.inventory.domain.models.InventoryInsufficientException;
import com.isfong.cnm.inventory.sdk.events.InventoryChangedEvent;
import com.isfong.cnm.inventory.sdk.events.InventoryDomainEvent;
import com.isfong.cnm.inventory.sdk.representations.InventoryRepresentation;
import com.isfong.cnm.shared.model.models.PersistableEntity;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder( access = AccessLevel.PRIVATE )
@AllArgsConstructor( access = AccessLevel.PRIVATE )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class Inventory extends PersistableEntity< Long > {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String productId;
    private String productName;
    private int remains;

    public static ResultWithDomainEvents< Inventory, InventoryDomainEvent > create( String productId, String productName ) {
        Inventory inventory = Inventory.builder( )
                .productId( productId )
                .productName( productName )
                .remains( 0 )
                .build( );
        return new ResultWithDomainEvents<>( inventory );
    }

    public ResultWithDomainEvents< Inventory, InventoryDomainEvent > changeProductName( String newName ) {
        this.productName = newName;
        return new ResultWithDomainEvents<>( this );
    }

    public InventoryRepresentation toRepresentation( ) {
        return new InventoryRepresentation( id, getCreatedAt( ), getCreatedAt( ), getVersion( ),
                productId, productName, remains );
    }

    public ResultWithDomainEvents< Inventory, InventoryDomainEvent > increase( int numberOfIncreaseInventory ) {
        this.remains = this.remains + numberOfIncreaseInventory;
        return new ResultWithDomainEvents<>( this, new InventoryChangedEvent( productId, remains ) );
    }

    public ResultWithDomainEvents< Inventory, InventoryDomainEvent > decrease( int numberOfDecreaseInventory ) {
        if ( this.remains < numberOfDecreaseInventory ) {
            throw new InventoryInsufficientException( String.format( "Insufficient inventory, expectation of %d is actually %d", numberOfDecreaseInventory, this.remains ) );
        }
        this.remains = this.remains - numberOfDecreaseInventory;
        return new ResultWithDomainEvents<>( this, new InventoryChangedEvent( productId, remains ) );
    }
}
