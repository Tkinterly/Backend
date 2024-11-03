package com.tinkerly.tinkerly.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
public class Sessions {
    @Id
    private String userId;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Date expiry;

    protected Sessions() {}

    public Sessions(String userId, String token, Date expiry) {
        this.userId = userId;
        this.token = token;
        this.expiry = expiry;
    }
}
