package com.tinkerly.tinkerly.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
public class Profiles {
    @Id
    private String userId;

    @Column
    private String avatarId;

    @Column(nullable = false)
    private Date registrationDate;

    @Column(nullable = false)
    private int access;

    @Column(nullable = false)
    private float averageRating;

    protected Profiles() {}

    public Profiles(String userId, String avatarId, Date registrationDate, int access, float averageRating) {
        this.userId = userId;
        this.registrationDate = registrationDate;
        this.access = access;
        this.averageRating = averageRating;
    }
}
