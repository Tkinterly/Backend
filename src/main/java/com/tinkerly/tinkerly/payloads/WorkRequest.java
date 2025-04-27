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
    Profile worker;
    String workDetailsId;
    String description;
    int status;

    public WorkRequest(WorkRequests workRequests, Profile customer, Profile worker) {
        this.requestId = workRequests.getRequestId();
        this.workDetailsId = workRequests.getWorkDetailsId();
        this.description = workRequests.getDescription();
        this.status = workRequests.getStatus();

        this.customer = customer;
        this.worker = worker;
    }

    public WorkRequest() {}
}
