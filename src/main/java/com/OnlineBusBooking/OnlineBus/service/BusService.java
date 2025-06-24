package com.OnlineBusBooking.OnlineBus.service;

import com.OnlineBusBooking.OnlineBus.model.Bus;
import com.OnlineBusBooking.OnlineBus.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public Bus saveBus(Bus bus) {
        return busRepository.save(bus);
    }

    public List<Bus> getBusesByOperator(String operatorId) {
        return busRepository.findByOperatorId(operatorId);
    }

    public Optional<Bus> getBusById(String id) {
        return busRepository.findById(id);
    }
}
