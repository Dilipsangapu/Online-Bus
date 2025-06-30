// SeatLayout.java
package com.OnlineBusBooking.OnlineBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "seat_layouts")
@Data
public class SeatLayout {

    @Id
    private String id;
    private String busId;
    private List<Seat> seats;

    @Data
    public static class Seat {
        private String number;
        private String type;  // "seater" or "sleeper"
        private String deck;  // "lower" or "upper"
        private int price;
    }
}
