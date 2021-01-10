package com.isfong.cnm.inventory.infrastructure.persistence;

import com.isfong.cnm.inventory.domain.models.entities.Inventory;
import com.isfong.cnm.inventory.domain.repositories.InventoryRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface InventoryJpaPostgresqlPersistence extends PagingAndSortingRepository< Inventory, Long >,
        InventoryRepository {
    Optional< Inventory > findByProductId( String productId );
}
