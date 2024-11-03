package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkDetails;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkDetail {
    String id;
    int domain;
    String type;
    int recommendedPrice;

    public WorkDetail(WorkDetails workDetails) {
        this.id = workDetails.getId();
        this.domain = workDetails.getDomain();
        this.type = workDetails.getType();
        this.recommendedPrice = workDetails.getRecommendedPrice();
    }

    public WorkDetail() {}
}
