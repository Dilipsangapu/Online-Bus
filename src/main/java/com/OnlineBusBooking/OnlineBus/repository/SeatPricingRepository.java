package com.OnlineBusBooking.OnlineBus.repository;

import com.OnlineBusBooking.OnlineBus.model.SeatPricing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SeatPricingRepository extends MongoRepository<SeatPricing, String> {
    List<SeatPricing> findByBusId(String busId);
}
