package com.OnlineBusBooking.OnlineBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "buses")
public class Bus {
    @Id
    private String id;

    private String operatorId; // Agent's ID (foreign reference)
    private String operatorName;
    private String busName;
    private String busNumber;
    private String busType; // Sleeper, Seater, etc.
    private int totalSeats;
    private int sleeperCount;
    private int seaterCount;
    private boolean hasUpperDeck;
    private boolean hasLowerDeck;
    private String deckType;
}
