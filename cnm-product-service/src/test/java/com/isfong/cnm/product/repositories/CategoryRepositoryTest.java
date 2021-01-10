package com.isfong.cnm.product.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfong.cnm.product.DockerComposeTestSupport;
import com.isfong.cnm.product.domain.models.entities.Category;
import com.isfong.cnm.product.domain.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryRepositoryTest extends DockerComposeTestSupport {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void save( ) throws Exception {
        Category category = this.categoryRepository.save( Category.create( faker.name( ).title( ), faker.book( ).genre( ).strip( ) ).result );
        System.err.println( new ObjectMapper( ).writeValueAsString( category ) );
        assertThat( category ).isNotNull( );
    }
}