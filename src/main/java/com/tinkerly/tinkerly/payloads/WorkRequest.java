package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkRequests;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkRequest {
    String requestId;
    String customerId;
    String workerId;
    String workDetailsId;
    int biddingTier;

    public WorkRequest(WorkRequests workRequests) {
        this.requestId = workRequests.getRequestId();
        this.customerId = workRequests.getCustomerId();
        this.workerId = workRequests.getWorkerId();
        this.workDetailsId = workRequests.getWorkDetailsId();
        this.biddingTier = workRequests.getBiddingTier();
    }

    public WorkRequest() {}
}
