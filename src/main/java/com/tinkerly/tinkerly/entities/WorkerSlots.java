package com.tinkerly.tinkerly.entities;

import com.tinkerly.tinkerly.payloads.TimeSlots;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
public class WorkerSlots {
    @Id
    private String id;

    @Column(nullable = false)
    private String workerId;

    @Column(nullable = false)
    private int day;

    @Column(nullable = false)
    private Date startTime;

    @Column(nullable = false)
    private Date endTime;

    protected WorkerSlots() {}

    public WorkerSlots(String workerId, int day, TimeSlots timeSlots) {
        this.id = UUID.randomUUID().toString();
        this.workerId = workerId;
        this.day = day;
        this.startTime = timeSlots.getStartTime();
        this.endTime = timeSlots.getEndTime();
    }
}
