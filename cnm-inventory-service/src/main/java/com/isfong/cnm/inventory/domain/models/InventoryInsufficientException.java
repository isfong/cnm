package com.isfong.cnm.inventory.domain.models;

public class InventoryInsufficientException extends RuntimeException {
    public InventoryInsufficientException( String msg ) {
        super( msg );
    }
}
