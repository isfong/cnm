package com.isfong.cnm.inventory.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfong.cnm.inventory.DockerComposeTestSupport;
import com.isfong.cnm.inventory.application.services.InventoryApplicationService;
import com.isfong.cnm.inventory.domain.models.entities.Inventory;
import com.isfong.cnm.inventory.domain.repositories.InventoryRepository;
import com.isfong.cnm.product.sdk.events.ProductCreatedEvent;
import com.isfong.cnm.product.sdk.events.ProductDomainEvent;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryApplicationServiceTest extends DockerComposeTestSupport {

    @Autowired
    DomainEventPublisher domainEventPublisher;
    @Autowired
    InventoryApplicationService inventoryApplicationService;
    @Autowired
    InventoryRepository inventoryRepository;

    @Test
    void createInventory( ) throws InterruptedException {
        // Given
        String aggregateType = ProductDomainEvent.AggregateType;
        String aggregateId = "product-id-test-0101";
        List< DomainEvent > events = List.of( new ProductCreatedEvent( "Domain-Driven Design",
                "Tackling Complexity in the Heart of Software", BigDecimal.valueOf( 524.93 ), "book" ) );
        // When
        domainEventPublisher.publish( aggregateType, aggregateId, events );
        Thread.sleep( 3000 );

        // Then
        Optional< Inventory > orInventory = inventoryRepository.findByProductId( aggregateId );
        orInventory.ifPresent( inventory -> {
            try {
                System.err.println( new ObjectMapper( ).writeValueAsString( inventory ) );
            } catch ( JsonProcessingException e ) {
                e.printStackTrace( );
            }
        } );
        assertThat( orInventory ).isPresent( );
    }

    @Test
    void changeProductName( ) {
    }
}