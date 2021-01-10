package com.isfong.cnm.inventory.domain.repositories;

import com.isfong.cnm.inventory.domain.models.entities.Inventory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository {
    Iterable< Inventory > findAll( );

    Inventory save( Inventory inventory );

    Optional< Inventory > findByProductId( String productId );

    Optional< Inventory > findById( long id );
}
