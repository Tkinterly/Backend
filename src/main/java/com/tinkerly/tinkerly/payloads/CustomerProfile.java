package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.CustomerProfiles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerProfile {
    int bookingSlots;

    public CustomerProfile(CustomerProfiles customerProfile) {
        this.bookingSlots = customerProfile.getBookingSlots();
    }

    public CustomerProfile(int bookingSlots) {
        this.bookingSlots = bookingSlots;
    }

    public CustomerProfile() {}
}
