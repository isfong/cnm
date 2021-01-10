package com.isfong.cnm.order.sdk.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class Address {
    String province;
    String city;
    String detail;

    public Address( String province, String city, String detail ) {
        this.province = province;
        this.city = city;
        this.detail = detail;
    }
}
