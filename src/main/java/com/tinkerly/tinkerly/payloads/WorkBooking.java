package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkBookings;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WorkBooking {
    String bookingId;
    String workDetailsId;
    double workPrice;
    Profile workerProfile;
    Profile customerProfile;
    Date startDate;
    Date endDate;
    int status;
    String description;

    public WorkBooking(WorkBookings workBooking, Profile workerProfile, Profile customerProfile) {
        this.bookingId = workBooking.getBookingId();
        this.workDetailsId = workBooking.getWorkDetailsId();
        this.workPrice = workBooking.getWorkPrice();
        this.workerProfile = workerProfile;
        this.customerProfile = customerProfile;
        this.startDate = workBooking.getStartDate();
        this.endDate = workBooking.getEndDate();
        this.status = workBooking.getStatus();
        this.description = workBooking.getDescription();
    }

    public WorkBooking() {}

    public WorkBooking(
            String bookingId,
            String workDetailsId,
            double workPrice,
            Profile workerProfile,
            Profile customerProfile,
            Date startDate,
            Date endDate,
            int status,
            String description
    ) {
        this.bookingId = bookingId;
        this.workDetailsId = workDetailsId;
        this.workPrice = workPrice;
        this.workerProfile = workerProfile;
        this.customerProfile = customerProfile;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
    }
}
