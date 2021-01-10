package com.isfong.cnm.order.sdk.representations;

import com.isfong.cnm.order.sdk.models.Address;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class OrderRepresentation {
    String id;
    long createdAt;
    long updatedAt;
    long version;
    BigDecimal totalPrice;
    String state;
    List< OrderItemRepresentation > items;
    Address address;
}
