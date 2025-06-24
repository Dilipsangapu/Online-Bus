package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.TripSchedule;
import com.OnlineBusBooking.OnlineBus.service.TripScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class TripScheduleController {
    @Autowired
    private TripScheduleService service;

    @PostMapping("/add")
    public ResponseEntity<TripSchedule> add(@RequestBody TripSchedule schedule) {
        return ResponseEntity.ok(service.saveSchedule(schedule));
    }

    @GetMapping("/by-bus/{busId}")
    public ResponseEntity<List<TripSchedule>> get(@PathVariable String busId) {
        return ResponseEntity.ok(service.getSchedulesByBusId(busId));
    }
}
