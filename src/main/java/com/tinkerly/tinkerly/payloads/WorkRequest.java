package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkRequests;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WorkRequest {
    String requestId;
    Profile customer;
    String workerId;
    String workDetailsId;
    String description;
    int status;

    public WorkRequest(WorkRequests workRequests, Profile customer) {
        this.requestId = workRequests.getRequestId();
        this.workerId = workRequests.getWorkerId();
        this.workDetailsId = workRequests.getWorkDetailsId();
        this.description = workRequests.getDescription();
        this.status = workRequests.getStatus();

        this.customer = customer;
    }

    public WorkRequest() {}
}
