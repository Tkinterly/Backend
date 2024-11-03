package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetail {
    String userId;
    String name;
    String email;
    String phone;
    int age;
    String address;
    int nic;

    public UserDetail(com.tinkerly.tinkerly.entities.UserDetails userDetails) {
        this.userId = userDetails.getUserId();
        this.name = userDetails.getName();
        this.email = userDetails.getEmail();
        this.phone = userDetails.getPhone();
        this.age = userDetails.getAge();
        this.address = userDetails.getAddress();
        this.nic = userDetails.getNic();
    }

    public UserDetail() {}
}
