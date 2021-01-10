package com.isfong.cnm.product.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfong.cnm.product.DockerComposeTestSupport;
import com.isfong.cnm.product.sdk.commands.CreateCategoryCommand;
import com.isfong.cnm.product.sdk.representations.CategoryRepresentation;
import org.junit.jupiter.api.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class CategoryControllerTest extends DockerComposeTestSupport {

    @Test
    void getCategory( ) throws JsonProcessingException {
        // given
        String id = given( ).body( new CreateCategoryCommand( faker.name( ).name( ), faker.food( ).measurement( ) ) ).contentType( APPLICATION_JSON_VALUE )
                .when( ).post( url( "/categories" ) )
                .then( ).statusCode( 201 ).extract( ).path( "id" );

        // when
        CategoryRepresentation categoryRepresentation = given( ).contentType( APPLICATION_JSON_VALUE )
                .when( ).get( url( "/categories/{id}" ), id )
                .then( ).statusCode( 200 ).extract( ).body( ).as( CategoryRepresentation.class );
        System.err.println( new ObjectMapper( ).writeValueAsString( categoryRepresentation ) );

        // then
        assertThat( categoryRepresentation ).isNotNull( );
    }
}