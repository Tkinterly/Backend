package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListingsResponse<T> {
    List<T> listings;
    List<BidRequest> bidRequests;

    public ListingsResponse() {}

    public ListingsResponse(List<T> listings, List<BidRequest> bidRequests) {
        this.listings = listings;
        this.bidRequests = bidRequests;
    }
}
