package com.isfong.cnm.order.sdk.commands;

import com.isfong.cnm.order.sdk.models.Address;
import lombok.Value;

import java.util.List;

@Value
public class CreateOrderCommand {
    List< CreateOrderItemCommand > items;
    Address address;
}
