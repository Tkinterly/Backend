package com.tinkerly.tinkerly.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class WorkerSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String skill;

    protected WorkerSkills() {}

    public WorkerSkills(String userId, String skill) {
        this.userId = userId;
        this.skill = skill;
    }

    public String toString() {
        return this.skill;
    }
}
