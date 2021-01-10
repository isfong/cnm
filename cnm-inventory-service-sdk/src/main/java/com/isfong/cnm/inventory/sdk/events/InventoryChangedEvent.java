package com.isfong.cnm.inventory.sdk.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class InventoryChangedEvent extends InventoryDomainEvent {
    private String productId;
    private int remains;

    public InventoryChangedEvent( String productId, int remains ) {
        this.productId = productId;
        this.remains = remains;
    }
}
