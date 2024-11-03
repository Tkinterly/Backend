package com.tinkerly.tinkerly.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class WorkerDomains {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private int domain;

    protected WorkerDomains() {}

    public WorkerDomains(String userId, int domain) {
        this.userId = userId;
        this.domain = domain;
    }

    public int toValue() {
        return this.domain;
    }
}
