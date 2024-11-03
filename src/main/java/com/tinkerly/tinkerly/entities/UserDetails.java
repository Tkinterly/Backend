package com.tinkerly.tinkerly.entities;

import com.tinkerly.tinkerly.payloads.UserDetail;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class UserDetails {
    @Id
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int nic;

    protected UserDetails() {}

    public UserDetails(
            String userId,
            String name,
            String email,
            String phone,
            int age,
            String address,
            int nic
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.address = address;
        this.nic = nic;
    }

    public UserDetails(UserDetail userDetail) {
        this.userId = userDetail.getUserId();
        this.name = userDetail.getName();
        this.email = userDetail.getEmail();
        this.phone = userDetail.getPhone();
        this.age = userDetail.getAge();
        this.address = userDetail.getAddress();
        this.nic = userDetail.getNic();
    }
}
