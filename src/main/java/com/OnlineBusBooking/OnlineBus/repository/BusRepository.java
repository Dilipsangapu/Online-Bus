package com.OnlineBusBooking.OnlineBus.repository;

import com.OnlineBusBooking.OnlineBus.model.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BusRepository extends MongoRepository<Bus, String> {
    List<Bus> findByOperatorId(String operatorId);
    List<Bus> findAll();
    List<Bus> findBySourceIgnoreCaseAndDestinationIgnoreCase(String source, String destination);
}
