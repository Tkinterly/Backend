package com.tinkerly.tinkerly.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class WorkBookings {
    @Id
    String bookingId;

    @Column(nullable = false)
    String workDetailsId;

    @Column(nullable = false)
    String workerId;

    @Column(nullable = false)
    int biddingTier;

    @Column(nullable = false)
    int workPrice;

    protected  WorkBookings() {}

    public WorkBookings(
       String bookingId,
       String workDetailsId,
       String workerId,
       int biddingTier,
       int workPrice
    ) {
        this.bookingId = bookingId;
        this.workDetailsId = workDetailsId;
        this.workerId = workerId;
        this.biddingTier = biddingTier;
        this.workPrice = workPrice;
    }
}
