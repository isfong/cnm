package com.isfong.cnm.order.sdk.commands;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateOrderItemCommand {
    String productId;
    int count;
    BigDecimal unitPrice;
}
