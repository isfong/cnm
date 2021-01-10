package com.isfong.cnm.inventory.application.services;

import com.isfong.cnm.inventory.domain.events.InventoryDomainEventPublisher;
import com.isfong.cnm.inventory.domain.models.InventoryNotFountException;
import com.isfong.cnm.inventory.domain.models.entities.Inventory;
import com.isfong.cnm.inventory.domain.repositories.InventoryRepository;
import com.isfong.cnm.inventory.sdk.events.InventoryDomainEvent;
import com.isfong.cnm.inventory.sdk.representations.InventoryRepresentation;
import com.isfong.cnm.order.sdk.representations.OrderItemRepresentation;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.of;

@Service
public class InventoryApplicationService {
    private final InventoryRepository inventoryRepository;
    private final InventoryDomainEventPublisher inventoryDomainEventPublisher;

    public InventoryApplicationService( InventoryRepository inventoryRepository, InventoryDomainEventPublisher inventoryDomainEventPublisher ) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryDomainEventPublisher = inventoryDomainEventPublisher;
    }

    @Transactional
    public void createInventory( String productId, String name ) {
        ResultWithDomainEvents< Inventory, InventoryDomainEvent > resultWithDomainEvents = Inventory.create( productId, name );
        Inventory inventory = this.inventoryRepository.save( resultWithDomainEvents.result );
        this.inventoryDomainEventPublisher.publish( inventory, resultWithDomainEvents.events );
    }

    public void changeProductName( String productId, String newName ) {
        Inventory inventory = this.inventoryRepository.findByProductId( productId )
                .orElseThrow( ( ) -> new InventoryNotFountException( Map.of( "productId", productId ) ) );
        ResultWithDomainEvents< Inventory, InventoryDomainEvent > created = inventory.changeProductName( newName );
        this.inventoryDomainEventPublisher.publish( inventory, created.events );
    }

    public Optional< InventoryRepresentation > getInventory( long id ) {
        return this.inventoryRepository.findById( id ).map( Inventory::toRepresentation );
    }

    @Transactional
    public void increaseInventory( long id, int numberOfIncreaseInventory ) {
        Inventory inventory = this.inventoryRepository.findById( id )
                .orElseThrow( ( ) -> new InventoryNotFountException( Map.of( "id", id ) ) );
        ResultWithDomainEvents< Inventory, InventoryDomainEvent > increased =
                inventory.increase( numberOfIncreaseInventory );
        this.inventoryDomainEventPublisher.publish( inventory, increased.events );
    }

    public void decreaseInventory( List< OrderItemRepresentation > items ) {
        items.forEach( product -> {
            ResultWithDomainEvents< Inventory, InventoryDomainEvent > decreased = this.inventoryRepository.findByProductId( product.getProductId( ) )
                    .orElseThrow( ( ) -> new InventoryNotFountException( of( "productId", product.getProductId( ) ) ) )
                    .decrease( product.getCount( ) );
            this.inventoryDomainEventPublisher.publish( decreased.result, decreased.events );
        } );
    }

    public Optional< InventoryRepresentation > getInventoryWithProduct( String productId ) {
        return this.inventoryRepository.findByProductId( productId ).map( Inventory::toRepresentation );
    }
}
