package com.isfong.cnm.product.sdk.representations;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductRepresentation {
    String id;
    String name;
    String description;
    BigDecimal price;
    Long createdAt;
    int inventory;
    String categoryId;
}
