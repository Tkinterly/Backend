package com.tinkerly.tinkerly.entities;

import com.tinkerly.tinkerly.payloads.WorkRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class WorkRequests {
    @Id
    private String requestId;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String workerId;

    @Column(nullable = false)
    private String workDetailsId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int status;

    protected WorkRequests() {}

    public WorkRequests(WorkRequest workRequest) {
        this.requestId = workRequest.getRequestId();
        this.customerId = workRequest.getCustomer().getUserId();
        this.workerId = workRequest.getWorker().getUserId();
        this.workDetailsId = workRequest.getWorkDetailsId();
        this.description = workRequest.getDescription();
        this.status = workRequest.getStatus();
    }

    public static WorkRequests create() {
        return new WorkRequests();
    }
}
