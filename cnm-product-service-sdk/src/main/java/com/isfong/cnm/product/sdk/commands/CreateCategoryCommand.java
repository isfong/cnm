package com.isfong.cnm.product.sdk.commands;

import lombok.Value;

@Value
public class CreateCategoryCommand {
    String name;
    String description;
}
