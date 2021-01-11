package com.isfong.cnm.api.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class ProductServiceTest {

    @Value( "${local.server.port}" )
    int port;

    String url( @SuppressWarnings( "SameParameterValue" ) String basePath ) {
        return "http://localhost:" + port + basePath;
    }

    @Test
    void productsTest( ) throws JsonProcessingException {
        List< Object > representations =
                given( ).contentType( APPLICATION_JSON_VALUE )
                        .when( ).get( url( "/products" ) )
                        .then( ).statusCode( 200 ).extract( ).path( "representations" );
        System.err.println( new ObjectMapper( ).writeValueAsString( representations ) );
        assertThat( representations ).isEmpty( );
    }


}
