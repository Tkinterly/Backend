package com.tinkerly.tinkerly.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class BidRequests {
    @Id
    String requestId;

    @Column(nullable = false)
    int biddingTier;

    protected BidRequests() {}

    public BidRequests(String requestId, int biddingTier) {
        this.requestId = requestId;
        this.biddingTier = biddingTier;
    }
}
