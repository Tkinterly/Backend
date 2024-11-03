package com.tinkerly.tinkerly.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class CustomerProfiles {
    @Id
    private String userId;

    @Column(nullable = false)
    private int bookingSlots;

    protected CustomerProfiles() {}

    public CustomerProfiles(String userId, int bookingSlots) {
        this.userId = userId;
        this.bookingSlots = bookingSlots;
    }
}
