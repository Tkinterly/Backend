package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.Reports;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
    String reportId;
    WorkBooking booking;
    String reason;

    public Report() {}

    public Report(Reports reports, WorkBooking booking) {
        this.reportId = reports.getReportId();
        this.reason = reports.getReason();

        this.booking = booking;
    }
}
