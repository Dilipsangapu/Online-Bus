package com.OnlineBusBooking.OnlineBus.service;

import com.OnlineBusBooking.OnlineBus.model.SeatPricing;
import com.OnlineBusBooking.OnlineBus.repository.SeatPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatPricingService {
    @Autowired
    private SeatPricingRepository repo;

    public List<SeatPricing> saveAll(List<SeatPricing> seatPricingList) {
        return repo.saveAll(seatPricingList);
    }

    public List<SeatPricing> getByBusId(String busId) {
        return repo.findByBusId(busId);
    }
}
