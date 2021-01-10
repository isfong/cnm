package com.isfong.cnm.inventory.resource;

import com.isfong.cnm.inventory.application.services.InventoryApplicationService;
import com.isfong.cnm.inventory.sdk.commands.IncreaseInventoryCommand;
import com.isfong.cnm.inventory.sdk.representations.InventoryRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/inventories" )
public class InventoryController {

    private final InventoryApplicationService inventoryApplicationService;

    public InventoryController( InventoryApplicationService inventoryApplicationService ) {
        this.inventoryApplicationService = inventoryApplicationService;
    }

    @GetMapping( "/{id}" )
    public ResponseEntity< InventoryRepresentation > getInventory( @PathVariable long id ) {
        return this.inventoryApplicationService.getInventory( id )
                .map( ResponseEntity::ok )
                .orElseGet( ( ) -> ResponseEntity.notFound( ).build( ) );
    }

    @GetMapping( "/with-product/{productId}" )
    public ResponseEntity< InventoryRepresentation > getInventoryWithProduct( @PathVariable String productId ) {
        return this.inventoryApplicationService.getInventoryWithProduct( productId )
                .map( ResponseEntity::ok )
                .orElseGet( ( ) -> ResponseEntity.notFound( ).build( ) );
    }

    @PostMapping( "/{id}/increase" )
    public ResponseEntity< Void > increaseInventory( @PathVariable long id,
                                                     @RequestBody IncreaseInventoryCommand command ) {
        this.inventoryApplicationService.increaseInventory( id, command.getNumberOfIncreaseInventory( ) );
        return ResponseEntity.noContent( ).build( );
    }
}
