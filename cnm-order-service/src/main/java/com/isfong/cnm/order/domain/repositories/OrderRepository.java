package com.isfong.cnm.order.domain.repositories;

import com.isfong.cnm.order.domain.models.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository {
    Order save( Order order );

    Optional< Order > findById( String id );

    Iterable<Order> findAll( Pageable page );

    long count( );
}
