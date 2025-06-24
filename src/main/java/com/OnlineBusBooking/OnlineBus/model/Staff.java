package com.OnlineBusBooking.OnlineBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "staff")
        public class Staff {
    @Id
    private String id;

    private String busId;
    private String driverName;
    private String driverContact;
    private String conductorName;
    private String conductorContact;
}
