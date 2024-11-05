package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerProposal {
    Profile workerProfile;
    List<Integer> recommendedPrice;

    public WorkerProposal() {}

    public WorkerProposal(Profile workerProfile, List<Integer> recommendedPrice) {
        this.workerProfile = workerProfile;
        this.recommendedPrice = recommendedPrice;
    }
}
