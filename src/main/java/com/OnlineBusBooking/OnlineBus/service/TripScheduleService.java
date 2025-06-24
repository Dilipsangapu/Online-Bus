package com.OnlineBusBooking.OnlineBus.service;

import com.OnlineBusBooking.OnlineBus.model.TripSchedule;
import com.OnlineBusBooking.OnlineBus.repository.TripScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripScheduleService {
    @Autowired
    private TripScheduleRepository repo;

    public TripSchedule saveSchedule(TripSchedule schedule) {
        return repo.save(schedule);
    }

    public List<TripSchedule> getSchedulesByBusId(String busId) {
        return repo.findByBusId(busId);
    }
}
