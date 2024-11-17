package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.UserBookings;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBooking {
    String bookingId;
    int status;

    @Setter
    WorkBooking workBooking;

    public UserBooking(UserBookings userBookings, WorkBooking workBooking) {
        this.bookingId = userBookings.getBookingId();
        this.status = userBookings.getStatus();
        this.workBooking = workBooking;
    }

    public UserBooking() {}

    public UserBooking(String bookingId, int status, WorkBooking workBooking) {
        this.bookingId = bookingId;
        this.status = status;
        this.workBooking = workBooking;
    }
}
