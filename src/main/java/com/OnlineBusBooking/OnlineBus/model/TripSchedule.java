package com.OnlineBusBooking.OnlineBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document(collection = "trip_schedules")
public class TripSchedule {
    @Id
    private String id;
    private String busId;
    private String routeId;
    private LocalDate date;
    private String departureTime;
    private String arrivalTime;



}
