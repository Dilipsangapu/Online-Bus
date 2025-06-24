package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.Staff;
import com.OnlineBusBooking.OnlineBus.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    private StaffService service;

    @PostMapping("/add")
    public ResponseEntity<Staff> add(@RequestBody Staff staff) {
        return ResponseEntity.ok(service.saveStaff(staff));
    }

    @GetMapping("/by-bus/{busId}")
    public ResponseEntity<List<Staff>> get(@PathVariable String busId) {
        return ResponseEntity.ok(service.getStaffByBusId(busId));
    }
}
