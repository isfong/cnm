package com.isfong.cnm.api.gateway.orders;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class OrderServiceProxy {

    public Mono< Object > getOrders( Optional<Integer> page, Optional<Integer> size ) {
        return WebClient.create( "/orders" )
                .get( )
                .uri( uriBuilder -> uriBuilder.queryParamIfPresent( "page", page ).queryParamIfPresent( "size", size ).build( ) )
                .retrieve( )
                .bodyToMono( Object.class )
                .onErrorResume( WebClientResponseException.class, Mono::error );
    }
}
