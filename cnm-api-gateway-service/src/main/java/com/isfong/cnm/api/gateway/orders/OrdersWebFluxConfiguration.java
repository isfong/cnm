package com.isfong.cnm.api.gateway.orders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;


@Configuration
@EnableWebFlux
public class OrdersWebFluxConfiguration implements WebFluxConfigurer {

    @Bean
    public RouterFunction< ? > getOrderRouterFunction( OrderAggregateHandler orderAggregateHandler ) {
        //noinspection NullableProblems
        return RouterFunctions.route( )
                .GET( "/orders/aggregates", orderAggregateHandler::getOrders )
                .build( );
    }
}
