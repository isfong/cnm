package com.isfong.cnm.product.resource;

import com.isfong.cnm.product.DockerComposeTestSupport;
import com.isfong.cnm.product.sdk.commands.ChangeProductNameCommand;
import com.isfong.cnm.product.sdk.commands.CreateProductCommand;
import com.isfong.cnm.product.sdk.representations.ProductRepresentation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class ProductControllerTest extends DockerComposeTestSupport {

    String productId;
    String originalName;

    @Test
    void createProduct( ) {
        String name = faker.name( ).name( );
        String description = faker.friends( ).location( );
        BigDecimal price = BigDecimal.valueOf( 5989.56 );
        String categoryId = faker.app( ).version( );
        String id = given( ) // Given
                .body( new CreateProductCommand( name, description, price, categoryId ) )
                .contentType( APPLICATION_JSON_VALUE )
                .when( ) // When
                .post( url( "/products" ) )
                .then( ) // Then
                .statusCode( 201 )
                .extract( )
                .path( "id" );
        assertThat( id ).isNotNull( );
        originalName = name;
        productId = id;
    }

    @Test
    void pagedRepresentations( ) {
        this.createProduct( );
        List< ProductRepresentation > representations = given( ).contentType( APPLICATION_JSON_VALUE )
                .when( ).get( url( "/products" ) )
                .then( ).statusCode( 200 ).extract( ).path( "representations" );
        assertThat( representations ).hasSize( 1 );
    }

    @Test
    void changeProductName( ) {
        this.createProduct( );

        given( ).body( new ChangeProductNameCommand( "好运来洗发水" ) ).contentType( APPLICATION_JSON_VALUE )
                .when( ).put( url( "/products/{id}/name" ), productId )
                .then( ).statusCode( 204 );

        String newName = given( ).contentType( APPLICATION_JSON_VALUE )
                .when( ).get( url( "/products/{id}" ), productId )
                .then( ).statusCode( 200 ).extract( ).path( "name" );

        System.err.println( "originalName: " + originalName );
        System.err.println( "newName: " + newName );
        assertThat( newName ).isNotEqualTo( this.originalName );
    }
}