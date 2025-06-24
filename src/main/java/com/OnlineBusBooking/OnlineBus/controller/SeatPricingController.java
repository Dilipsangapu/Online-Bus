package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.SeatPricing;
import com.OnlineBusBooking.OnlineBus.service.SeatPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing")
public class SeatPricingController {
    @Autowired
    private SeatPricingService service;

    @PostMapping("/add-bulk")
    public ResponseEntity<List<SeatPricing>> addBulk(@RequestBody List<SeatPricing> prices) {
        return ResponseEntity.ok(service.saveAll(prices));
    }

    @GetMapping("/by-bus/{busId}")
    public ResponseEntity<List<SeatPricing>> get(@PathVariable String busId) {
        return ResponseEntity.ok(service.getByBusId(busId));
    }
}
