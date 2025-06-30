// SeatLayoutController.java
package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.SeatLayout;
import com.OnlineBusBooking.OnlineBus.repository.SeatLayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/seats")
public class SeatLayoutController {

    @Autowired
    private SeatLayoutRepository seatLayoutRepository;

    @PostMapping("/save")
    public SeatLayout saveLayout(@RequestBody SeatLayout layout) {
        // overwrite if bus layout already exists
        Optional<SeatLayout> existing = seatLayoutRepository.findByBusId(layout.getBusId());
        existing.ifPresent(value -> layout.setId(value.getId()));
        return seatLayoutRepository.save(layout);
    }

    @GetMapping("/by-bus/{busId}")
    public Optional<SeatLayout> getLayoutByBusId(@PathVariable String busId) {
        return seatLayoutRepository.findByBusId(busId);
    }
}
