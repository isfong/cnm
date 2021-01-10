package com.isfong.cnm.inventory.sdk.representations;

import lombok.Value;

@Value
public class InventoryRepresentation {
    long id;
    long createdAt;
    long updatedAt;
    long version;
    String productId;
    String productName;
    int remains;
}
