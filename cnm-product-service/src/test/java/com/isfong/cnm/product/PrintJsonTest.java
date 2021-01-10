package com.isfong.cnm.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfong.cnm.inventory.sdk.commands.IncreaseInventoryCommand;
import com.isfong.cnm.order.sdk.commands.CreateOrderCommand;
import com.isfong.cnm.order.sdk.commands.CreateOrderItemCommand;
import com.isfong.cnm.order.sdk.models.Address;
import com.isfong.cnm.product.sdk.commands.CreateCategoryCommand;
import com.isfong.cnm.product.sdk.commands.CreateProductCommand;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class PrintJsonTest {

    @Test
    void json( ) throws JsonProcessingException {
        ObjectMapper json = new ObjectMapper( );
        CreateCategoryCommand cncf = new CreateCategoryCommand( "云原生", "云原生开发开发实践" );
        System.err.println( json.writeValueAsString( cncf ) );

        CreateProductCommand createProductCommand = new CreateProductCommand( "Domain-Driven Design",
                "Tackling Complexity in the Heart of Software",
                BigDecimal.valueOf( 524.93 ),
                null );
        System.err.println( json.writeValueAsString( createProductCommand ) );

        IncreaseInventoryCommand increaseInventoryCommand = new IncreaseInventoryCommand( 10 );
        System.err.println( json.writeValueAsString( increaseInventoryCommand ) );

        List< CreateOrderItemCommand > is = List.of(
                new CreateOrderItemCommand( "111", 1, BigDecimal.valueOf( 10.05 ) )
        );
        CreateOrderCommand createOrderCommand = new CreateOrderCommand( is, new Address( "Shanghai", "上海", "黄河大合唱" ) );
        System.err.println( json.writeValueAsString( createOrderCommand ) );
    }
}
