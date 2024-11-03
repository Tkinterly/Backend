package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkBookings;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkBooking {
    String bookingId;
    String workDetailsId;
    int biddingTier;
    int workPrice;

    @Setter
    Profile workerProfile;

    public WorkBooking(WorkBookings workBooking, Profile workerProfile) {
        this.bookingId = workBooking.getBookingId();
        this.biddingTier = workBooking.getBiddingTier();
        this.workDetailsId = workBooking.getWorkDetailsId();
        this.workPrice = workBooking.getWorkPrice();
        this.workerProfile = workerProfile;
    }

    public WorkBooking() {}
}
