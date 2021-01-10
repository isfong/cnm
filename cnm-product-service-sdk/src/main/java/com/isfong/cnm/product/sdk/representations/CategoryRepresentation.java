package com.isfong.cnm.product.sdk.representations;

import lombok.Value;

@Value
public class CategoryRepresentation {
    String id;
    String name;
    String description;
    long createdAt;
}
