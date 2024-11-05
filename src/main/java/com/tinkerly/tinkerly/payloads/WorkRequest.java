package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkRequests;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkRequest {
    String requestId;
    Profile customer;
    String workerId;
    String workDetailsId;
    int biddingTier;

    public WorkRequest(WorkRequests workRequests, Profile customer) {
        this.requestId = workRequests.getRequestId();
        this.workerId = workRequests.getWorkerId();
        this.workDetailsId = workRequests.getWorkDetailsId();
        this.biddingTier = workRequests.getBiddingTier();

        this.customer = customer;
    }

    public WorkRequest() {}
}
