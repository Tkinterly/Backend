package com.tinkerly.tinkerly.entities;

import com.tinkerly.tinkerly.payloads.WorkResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Date;

@Getter
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

    protected WorkResponses() {}

    public WorkResponses(WorkResponse workResponse) {
        this.workRequestId = workResponse.getWorkRequestId();
        this.startDate = workResponse.getStartDate();
        this.endDate = workResponse.getEndDate();
        this.cost = workResponse.getCost();
    }
}
