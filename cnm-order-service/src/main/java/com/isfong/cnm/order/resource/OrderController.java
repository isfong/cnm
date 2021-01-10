package com.isfong.cnm.order.resource;

import com.isfong.cnm.order.application.services.OrderApplicationService;
import com.isfong.cnm.order.sdk.commands.CreateOrderCommand;
import com.isfong.cnm.order.sdk.representations.CreateOrderRepresentation;
import com.isfong.cnm.order.sdk.representations.OrderRepresentation;
import com.isfong.cnm.shared.model.representations.PagedRepresentations;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping( "/orders" )
public class OrderController {
    private final OrderApplicationService applicationService;

    public OrderController( OrderApplicationService applicationService ) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity< CreateOrderRepresentation > createOrder( @RequestBody CreateOrderCommand command ) {
        String id = this.applicationService.createOrder( command );
        return ResponseEntity.created( ServletUriComponentsBuilder//
                .fromCurrentRequest( )
                .build( )
                .toUri( ) )
                .body( new CreateOrderRepresentation( id ) );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity< OrderRepresentation > getOrder( @PathVariable String id ) {
        return this.applicationService.getOrder( id )
                .map( ResponseEntity::ok )
                .orElseGet( ( ) -> ResponseEntity.notFound( ).build( ) );
    }

    @GetMapping
    public ResponseEntity< PagedRepresentations< OrderRepresentation > > getOrders( @RequestParam( required = false, defaultValue = "0" ) int page,
                                                                                    @RequestParam( required = false, defaultValue = "10" ) int size ) {
        return ResponseEntity.ok( this.applicationService.getOrders( PageRequest.of( page, size ) ) );
    }
}
