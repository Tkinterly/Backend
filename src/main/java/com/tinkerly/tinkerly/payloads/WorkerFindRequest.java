package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerFindRequest {
    String customerId;
    String workDetailsId;

    public WorkerFindRequest() {}
}
