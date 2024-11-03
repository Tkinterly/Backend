package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerProposal {
    Profile workerProfile;
    List<Float> recommendedPrice;

    public WorkerProposal() {}
}
