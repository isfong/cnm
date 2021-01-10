package com.isfong.cnm.product.resource;

import com.google.common.collect.ImmutableBiMap;
import com.isfong.cnm.product.application.services.CategoryApplicationService;
import com.isfong.cnm.product.sdk.commands.CreateCategoryCommand;
import com.isfong.cnm.product.sdk.representations.CategoryRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping( "/categories" )
public class CategoryController {
    private final CategoryApplicationService applicationService;

    public CategoryController( CategoryApplicationService applicationService ) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity< Map< String, String > > createCategory( @RequestBody CreateCategoryCommand command ) throws Exception {
        String id = this.applicationService.createCategory( command );
        return ResponseEntity.created( ServletUriComponentsBuilder//
                .fromCurrentRequest( )
                .path( "/{id}" )
                .build( id ) )
                .body( ImmutableBiMap.of( "id", id ) );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity< CategoryRepresentation > getCategory( @PathVariable String id ) {
        return this.applicationService.getCategory( id )
                .map( ResponseEntity::ok )
                .orElseGet( ( ) -> ResponseEntity.notFound( ).build( ) );
    }
}
