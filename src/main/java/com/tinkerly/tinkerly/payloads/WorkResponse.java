package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkResponses;
import lombok.Getter;

import java.util.Date;

@Getter
public class WorkResponse {
    private String workRequestId;
    private Date startDate;
    private Date endDate;
    private double cost;
    private int status;

    public WorkResponse() {}

    public WorkResponse(WorkResponses workResponse) {
        this.workRequestId = workResponse.getWorkRequestId();
        this.startDate = workResponse.getStartDate();
        this.endDate = workResponse.getEndDate();
        this.cost = workResponse.getCost();
        this.status = workResponse.getStatus();
    }
}
