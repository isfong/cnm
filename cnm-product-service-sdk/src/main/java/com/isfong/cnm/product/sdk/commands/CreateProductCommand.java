package com.isfong.cnm.product.sdk.commands;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateProductCommand {
    String name;
    String description;
    BigDecimal price;
    String categoryId;
}
