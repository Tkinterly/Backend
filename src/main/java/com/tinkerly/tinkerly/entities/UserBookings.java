package com.tinkerly.tinkerly.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class UserBookings {
    @Id
    private String bookingId;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private int status;

    protected UserBookings() {}

    public UserBookings(
            String bookingId,
            String customerId,
            int status
    ) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.status = status;
    }
}
