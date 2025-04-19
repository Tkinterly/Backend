package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DaySlots {
    private TimeSlots mon;
    private TimeSlots tue;
    private TimeSlots wed;
    private TimeSlots thu;
    private TimeSlots fri;
    private TimeSlots sat;
    private TimeSlots sun;
}
