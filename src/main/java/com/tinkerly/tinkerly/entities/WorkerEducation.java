package com.tinkerly.tinkerly.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class WorkerEducation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String education;

    protected WorkerEducation() {}

    public WorkerEducation(String userId, String education) {
        this.userId = userId;
        this.education = education;
    }

    public String toString() {
        return this.education;
    }
}
