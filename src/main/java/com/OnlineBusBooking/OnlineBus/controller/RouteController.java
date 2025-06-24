package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.Route;
import com.OnlineBusBooking.OnlineBus.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {
    @Autowired
    private RouteService service;

    @PostMapping("/add")
    public ResponseEntity<Route> add(@RequestBody Route route) {
        return ResponseEntity.ok(service.saveRoute(route));
    }

    @GetMapping("/by-bus/{busId}")
    public ResponseEntity<List<Route>> get(@PathVariable String busId) {
        return ResponseEntity.ok(service.getRoutesByBusId(busId));
    }
}
