package com.OnlineBusBooking.OnlineBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "trip_schedules")
public class TripSchedule {
    @Id
    private String id;

    private String busId;
    private String routeId;
    private String days; // e.g., "Mon, Tue", "Daily", "Weekend"
}
