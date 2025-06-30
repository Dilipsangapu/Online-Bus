package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.TripSchedule;
import com.OnlineBusBooking.OnlineBus.repository.TripScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class TripScheduleController {

    @Autowired
    private TripScheduleRepository repo;

    @PostMapping("/add")
    public TripSchedule add(@RequestBody TripSchedule schedule) {
        return repo.save(schedule);
    }

    @GetMapping("/by-bus/{busId}")
    public List<TripSchedule> getByBus(@PathVariable String busId) {
        return repo.findByBusId(busId);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        repo.deleteById(id);
    }
}
