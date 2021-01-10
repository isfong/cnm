package com.isfong.cnm.order.sdk.representations;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderItemRepresentation {
    String productId;
    int count;
    BigDecimal unitPrice;
}
