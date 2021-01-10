package com.isfong.cnm.product.domain.repositories;

import com.isfong.cnm.product.domain.models.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {
    Product save( Product product ) throws Exception;

    List< Product> findAll( Pageable pageable );

    int findCount( );

    Optional< Product> findById( String id );
}
