package com.isfong.cnm.product.domain.repositories;

import com.isfong.cnm.product.domain.models.entities.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository {
    Category save( Category category ) throws Exception;

    Optional< Category > findById( String id );
}
