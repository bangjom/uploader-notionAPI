package com.windfally.uploader.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {
    private String city;
    private String district;

    @Builder
    public Address(String city, String district) {
        this.city = city;
        this.district = district;
    }
}
