package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.BidRequests;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidRequest {
    String requestId;
    int biddingTier;

    public BidRequest(BidRequests bidRequests) {
        this.requestId = bidRequests.getRequestId();
        this.biddingTier = bidRequests.getBiddingTier();
    }

    public BidRequest() {}
}
