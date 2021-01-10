package com.isfong.cnm.order.domain.models;

import java.util.Map;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException( Map< String, Object > with ) {
        super( String.format( "Order not be found with %s", with ) );
    }
}
