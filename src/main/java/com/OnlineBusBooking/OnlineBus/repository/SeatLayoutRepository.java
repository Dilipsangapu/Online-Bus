// SeatLayoutRepository.java
package com.OnlineBusBooking.OnlineBus.repository;

import com.OnlineBusBooking.OnlineBus.model.SeatLayout;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SeatLayoutRepository extends MongoRepository<SeatLayout, String> {
    Optional<SeatLayout> findByBusId(String busId);
}
