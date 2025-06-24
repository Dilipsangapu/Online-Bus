package com.OnlineBusBooking.OnlineBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users") // Same collection as users, but role = agent
public class Agent {

    @Id
    private String id;

    private String name;             // Travels/Agency Name
    private String contactPerson;    // Contact person at agency
    private String email;
    private String phone;
    private String password;
    private String role = "agent";   // Default
}
