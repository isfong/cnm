package com.isfong.cnm.product.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.isfong.cnm.product.DockerComposeTestSupport;
import com.isfong.cnm.product.application.services.ProductApplicationService;
import com.isfong.cnm.product.sdk.representations.ProductRepresentation;
import com.isfong.cnm.shared.model.representations.PagedRepresentations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class ProductApplicationServiceTest extends DockerComposeTestSupport {

    @Autowired
    ProductApplicationService productApplicationService;

    @Test
    void createProduct( ) throws Exception {
        Faker faker = Faker.instance( Locale.CHINA );
        String id = productApplicationService.createProduct( faker.name( ).name( ),
                faker.food( ).measurement( ),
                BigDecimal.valueOf( 88.88 ),
                faker.idNumber( ).valid( ).strip( ) );
        assertThat( id ).isNotBlank( );
    }

    @Test
    void pagedRepresentations( ) throws Exception {
        this.createProduct( );
        PagedRepresentations< ProductRepresentation > representations = this.productApplicationService.pagedRepresentations( 1, 10 );
        String s = new ObjectMapper( ).writeValueAsString( representations );
        System.err.println( s );
    }
}