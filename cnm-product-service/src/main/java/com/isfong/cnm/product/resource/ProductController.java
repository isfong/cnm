package com.isfong.cnm.product.resource;

import com.google.common.collect.ImmutableBiMap;
import com.isfong.cnm.product.application.services.ProductApplicationService;
import com.isfong.cnm.product.sdk.commands.ChangeProductNameCommand;
import com.isfong.cnm.product.sdk.commands.CreateProductCommand;
import com.isfong.cnm.product.sdk.representations.ProductRepresentation;
import com.isfong.cnm.shared.model.representations.PagedRepresentations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping( "/products" )
public class ProductController {

    private final ProductApplicationService productApplicationService;

    public ProductController( ProductApplicationService productApplicationService ) {
        this.productApplicationService = productApplicationService;
    }

    @PostMapping
    public ResponseEntity< Map< String, String > > createProduct( @RequestBody CreateProductCommand command ) throws Exception {
        String id = this.productApplicationService.createProduct( command.getName( ),
                command.getDescription( ),
                command.getPrice( ),
                command.getCategoryId( ) );
        return ResponseEntity.created( ServletUriComponentsBuilder//
                .fromCurrentRequest( )
                .path( "/{id}" )
                .build( id ) )
                .body( ImmutableBiMap.of( "id", id ) );
    }

    @GetMapping
    public ResponseEntity< PagedRepresentations< ProductRepresentation > > pagedProducts(
            @RequestParam( required = false, defaultValue = "1" ) int page,
            @RequestParam( required = false, defaultValue = "10" ) int size ) {
        return ResponseEntity.ok( this.productApplicationService.pagedRepresentations( page, size ) );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity< ProductRepresentation > getProduct( @PathVariable String id ) {
        return this.productApplicationService.getProduct( id )
                .map( ResponseEntity::ok )
                .orElseGet( ( ) -> ResponseEntity.notFound( ).build( ) );
    }

    @PutMapping( "/{id}/name" )
    public ResponseEntity< Void > changeProductName( @PathVariable String id,
                                                     @RequestBody ChangeProductNameCommand command ) throws Exception {
        this.productApplicationService.changeProductName( id, command.getNewName( ) );
        return ResponseEntity.noContent( ).build( );
    }
}
