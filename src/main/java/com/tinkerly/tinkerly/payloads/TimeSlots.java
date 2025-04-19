package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkerSlots;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TimeSlots {
    private Date startTime;
    private Date endTime;

    public TimeSlots() {}

    public TimeSlots(WorkerSlots workerSlot) {
        this.startTime = workerSlot.getStartTime();
        this.endTime = workerSlot.getEndTime();
    }
}
