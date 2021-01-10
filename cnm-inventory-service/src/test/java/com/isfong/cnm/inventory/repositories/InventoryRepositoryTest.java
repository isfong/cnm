package com.isfong.cnm.inventory.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfong.cnm.inventory.DockerComposeTestSupport;
import com.isfong.cnm.inventory.domain.models.entities.Inventory;
import com.isfong.cnm.inventory.domain.repositories.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryRepositoryTest extends DockerComposeTestSupport {

    @Autowired
    InventoryRepository inventoryRepository;

    @Test
    void findAll( ) throws JsonProcessingException {
        // Given
        this.save( );
        this.save( );
        this.save( );
        this.save( );

        // When
        List< Inventory > inventories = StreamSupport.stream( inventoryRepository.findAll( ).spliterator( ), false )
                .collect( Collectors.toList( ) );

        // Then
        System.err.println( new ObjectMapper( ).writeValueAsString( inventories ) );
        assertThat( inventories ).isNotEmpty( ).hasSize( 4 );
    }

    @Test
    void save( ) {
        Inventory inventory = this.inventoryRepository.save( Inventory.create( faker.app( ).version( ), faker.name( ).name( ) ).result );
        assertThat( inventory ).isNotNull( );
    }
}