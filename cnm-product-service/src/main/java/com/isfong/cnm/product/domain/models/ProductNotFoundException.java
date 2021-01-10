package com.isfong.cnm.product.domain.models;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException( String id ) {
        super( String.format( "Product not be found with id:%s", id ) );
    }
}
