package com.tinkerly.tinkerly.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class WorkerReviews {

    @Id
    private String id;

    @Column(nullable = false)
    private String workerId;

    @Column(nullable = false)
    private String workBookingId;

    @Column(nullable = false)
    private String workDetailsId;

    @Column(nullable = false)
    private int domain;

    @Column(nullable = false)
    private String review;

    protected WorkerReviews() {}

    public WorkerReviews(String workerId, String workBookingId, String workDetailsId, int domain, String review) {
        this.id = UUID.randomUUID().toString();
        this.workerId = workerId;
        this.workBookingId = workBookingId;
        this.workDetailsId = workDetailsId;
        this.domain = domain;
        this.review = review;
    }
}
