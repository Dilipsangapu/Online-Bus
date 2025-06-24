package com.OnlineBusBooking.OnlineBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String email;
    private String phone;
    private int age;
    private String gender;
    private String password;
    private String role; // ✅ No default value
}
