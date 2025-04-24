package com.tinkerly.tinkerly.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class WorkBookings {
    @Id
    private String bookingId;

    @Column(nullable = false)
    private String workDetailsId;

    @Column(nullable = false)
    private String workerId;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private double workPrice;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private String description;

    protected  WorkBookings() {}

    public WorkBookings(
       String bookingId,
       String workDetailsId,
       String workerId,
       String customerId,
       double workPrice,
       Date startDate,
       Date endDate,
       int status
    ) {
        this.bookingId = bookingId;
        this.workDetailsId = workDetailsId;
        this.workerId = workerId;
        this.customerId = customerId;
        this.workPrice = workPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
}
