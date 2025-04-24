package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkBookings;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkBooking {
    String bookingId;
    String workDetailsId;
    double workPrice;
    Profile workerProfile;
    Profile customerProfile;

    public WorkBooking(WorkBookings workBooking, Profile workerProfile, Profile customerProfile) {
        this.bookingId = workBooking.getBookingId();
        this.workDetailsId = workBooking.getWorkDetailsId();
        this.workPrice = workBooking.getWorkPrice();
        this.workerProfile = workerProfile;
        this.customerProfile = customerProfile;
    }

    public WorkBooking() {}

    public WorkBooking(String bookingId, String workDetailsId, double workPrice, Profile workerProfile, Profile customerProfile) {
        this.bookingId = bookingId;
        this.workDetailsId = workDetailsId;
        this.workPrice = workPrice;
        this.workerProfile = workerProfile;
        this.customerProfile = customerProfile;
    }
}
