package com.isfong.cnm.inventory.domain.models;

import java.util.Map;

public class InventoryNotFountException extends RuntimeException {
    public InventoryNotFountException( Map< String, Object > reason ) {
        super( String.format( "Inventory not be found with %s", reason ) );
    }
}
