package com.isfong.cnm.product.repositories;

import com.github.javafaker.Faker;
import com.isfong.cnm.product.DockerComposeTestSupport;
import com.isfong.cnm.product.domain.models.entities.Product;
import com.isfong.cnm.product.domain.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepositoryTest extends DockerComposeTestSupport {
    @Autowired
    ProductRepository productRepository;
    String productId;

    @Test
    void save( ) throws Exception {
        Faker faker = Faker.instance( Locale.CHINA );
        Product product = Product.create( faker.name( ).name( ),
                faker.rockBand( ).name( ),
                BigDecimal.valueOf( 88.88 ),
                faker.idNumber( ).valid( ) ).result;
        product = productRepository.save( product );
        assertThat( product ).isNotNull( );
        this.productId = product.getId( );
//        docker.dockerCompose( ).down( );
    }

    @Test
    void findAll( ) throws Exception {
        this.save( );
        List< Product > products = this.productRepository.findAll( PageRequest.of( 1, 10 ) );
        assertThat( products ).hasSize( 1 );
    }

    @Test
    void findCount( ) throws Exception {
        this.save( );
        int count = this.productRepository.findCount( );
        assertThat( count ).isEqualTo( 1 );
    }

    @Test
    void findById( ) throws Exception {
        this.save( );
        Optional< Product > productOptional = this.productRepository.findById( productId )
                .map( product -> {
                    System.err.println( product.getName( ) );
                    return product;
                } );
        assertThat( productOptional.isPresent( ) );
    }
}