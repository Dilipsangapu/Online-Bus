package com.OnlineBusBooking.OnlineBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "routes")
public class Route {

    @Id
    private String id;

    private String busId;
    private String from;
    private String to;
    private List<String> stops;
    private String timings;


}
