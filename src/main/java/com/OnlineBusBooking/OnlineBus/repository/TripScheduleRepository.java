package com.OnlineBusBooking.OnlineBus.repository;

import com.OnlineBusBooking.OnlineBus.model.TripSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TripScheduleRepository extends MongoRepository<TripSchedule, String> {
    List<TripSchedule> findByBusId(String busId);
}
