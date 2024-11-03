package com.tinkerly.tinkerly.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class WorkerProfiles {
    @Id
    private String userId;

    @Column(nullable = false)
    private int yearsOfExperience;

    @Column()
    private Date suspension;

    @Column(nullable = false)
    private boolean banned;

    protected WorkerProfiles() {}

    public WorkerProfiles(String userId, int yearsOfExperience, Date suspension, boolean banned) {
        this.userId = userId;
        this.yearsOfExperience = yearsOfExperience;
        this.suspension = suspension;
        this.banned = banned;
    }
}
