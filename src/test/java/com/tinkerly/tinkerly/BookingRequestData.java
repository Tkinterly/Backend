package com.tinkerly.tinkerly;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingRequestData {
    String customerToken;
    String workerToken;
    String requestId;
    String workDetailsId;

}
