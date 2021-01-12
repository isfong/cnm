package com.isfong.cnm.api.gateway.orders;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class OrderAggregateHandler {

    private final OrderServiceProxy orderServiceProxy;

    public OrderAggregateHandler( OrderServiceProxy orderServiceProxy ) {
        this.orderServiceProxy = orderServiceProxy;
    }


    public Mono< ServerResponse > getOrders( ServerRequest request ) {
        Optional< Integer > page = request.queryParam( "page" ).map( Integer::valueOf );
        Optional< Integer > size = request.queryParam( "size" ).map( Integer::valueOf );
        return this.orderServiceProxy.getOrders( page, size )
                .flatMap( pagedRepresentations -> ok( ).contentType( MediaType.APPLICATION_JSON )
                        .bodyValue( pagedRepresentations ) )
                .switchIfEmpty( ServerResponse.notFound( ).build( ) );

    }
}
