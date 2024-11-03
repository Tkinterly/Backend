package com.tinkerly.tinkerly.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Reports {
    @Id
    private String reportId;

    @Column(nullable = false)
    private String bookingId;

    @Column(nullable = false)
    private String reason;

    protected Reports() {}

    public Reports(String reportId, String bookingId, String reason) {
        this.reportId = reportId;
        this.bookingId = bookingId;
        this.reason = reason;
    }
}
