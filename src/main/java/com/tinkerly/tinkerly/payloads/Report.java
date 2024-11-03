package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.Reports;
import com.tinkerly.tinkerly.entities.UserBookings;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
    String reportId;
    WorkBooking booking;
    String reason;

    public Report() {}
}
