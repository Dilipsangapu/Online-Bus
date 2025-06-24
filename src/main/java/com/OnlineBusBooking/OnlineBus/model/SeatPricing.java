package com.OnlineBusBooking.OnlineBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "seat_pricing")
public class SeatPricing {
    @Id
    private String id;

    private String busId;
    private int seatNo;
    private double price;
}
