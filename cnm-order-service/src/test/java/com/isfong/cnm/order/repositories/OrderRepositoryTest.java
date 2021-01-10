package com.isfong.cnm.order.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfong.cnm.order.DockerComposeTestSupport;
import com.isfong.cnm.order.domain.models.OrderItem;
import com.isfong.cnm.order.domain.models.entities.Order;
import com.isfong.cnm.order.domain.repositories.OrderRepository;
import com.isfong.cnm.order.sdk.events.OrderDomainEvent;
import com.isfong.cnm.order.sdk.models.Address;
import com.isfong.cnm.shared.model.utils.UUIDGenerator;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class OrderRepositoryTest extends DockerComposeTestSupport {

    @Autowired
    OrderRepository orderRepository;
    String id;

    @Test
    void save( ) throws JsonProcessingException {
        id = UUIDGenerator.newUUID( );
        List< OrderItem > items = List.of( );
        Address address = new Address( "广东省", "广州市", "番禺区华创产业园二期" );
        ResultWithDomainEvents< Order, OrderDomainEvent > resultWithDomainEvents = Order.create( id, items, address );
        Order order = orderRepository.save( resultWithDomainEvents.result );
        System.err.println( new ObjectMapper( ).writeValueAsString( order ) );
    }

    @Test
    void findById( ) throws JsonProcessingException {
        this.save( );
        this.orderRepository.findById( id ).ifPresent( order -> {
            try {
                System.err.println( "----findById---" );
                System.err.println( new ObjectMapper( ).writeValueAsString( order ) );
            } catch ( JsonProcessingException e ) {
                e.printStackTrace( );
            }
        } );
    }
}