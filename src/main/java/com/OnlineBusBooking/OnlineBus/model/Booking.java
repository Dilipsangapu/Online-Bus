package com.OnlineBusBooking.OnlineBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;

    private String busId;
    private String customerName;
    private String customerEmail;
    private String bookingDate;
    private String seatNumber;
    private double fare;
    private String status; // e.g., CONFIRMED, CANCELLED

    // Getters and Setters
}
