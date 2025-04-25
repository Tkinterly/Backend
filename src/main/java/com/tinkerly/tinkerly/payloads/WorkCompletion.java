package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkCompletion {
    String bookingId;
    Boolean isReported;
    String review;

    public WorkCompletion() {}
}
