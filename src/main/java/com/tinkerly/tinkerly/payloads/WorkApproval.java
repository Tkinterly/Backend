package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkApproval {
    String requestId;
    Boolean isApproved;

    public WorkApproval() {}
}
