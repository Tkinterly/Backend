package com.tinkerly.tinkerly.entities;

import com.tinkerly.tinkerly.payloads.WorkResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class WorkResponses {
    @Id
    private String workRequestId;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private double cost;

    @Column(nullable = false)
    private int status;

    protected WorkResponses() {}

    public WorkResponses(WorkResponse workResponse) {
        this.workRequestId = workResponse.getWorkRequestId();
        this.startDate = workResponse.getStartDate();
        this.endDate = workResponse.getEndDate();
        this.cost = workResponse.getCost();
        this.status = workResponse.getStatus();
    }
}
