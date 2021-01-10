package com.isfong.cnm.order.domain.models;

import com.isfong.cnm.order.sdk.representations.OrderItemRepresentation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Getter
@Embeddable
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class OrderItem {
    private String productId;
    private int count;
    private BigDecimal unitPrice;

    public OrderItem( String productId, int count, BigDecimal unitPrice ) {
        this.productId = productId;
        this.count = count;
        this.unitPrice = unitPrice;
    }

    public BigDecimal totalPrice( ) {
        return this.unitPrice.multiply( BigDecimal.valueOf( count ) );
    }

    public OrderItemRepresentation toRepresentation( ) {
        return new OrderItemRepresentation( productId, count, unitPrice );
    }
}
