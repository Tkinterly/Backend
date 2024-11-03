package com.tinkerly.tinkerly.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class WorkDetails {
    @Id
    private String id;

    @Column(nullable = false)
    private int domain;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int recommendedPrice;
}
