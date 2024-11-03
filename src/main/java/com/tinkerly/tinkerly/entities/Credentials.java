package com.tinkerly.tinkerly.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Credentials {
    @Id
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    protected Credentials() {}

    public Credentials(String userId, String username, String passwordHash) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
