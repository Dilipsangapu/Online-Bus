package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.Route;
import com.OnlineBusBooking.OnlineBus.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    // ✅ Add a new route
    @PostMapping("/add")
    public Route addRoute(@RequestBody Route route) {
        return routeRepository.save(route);
    }

    // ✅ Get routes by bus ID
    @GetMapping("/by-bus/{busId}")
    public List<Route> getRoutesByBus(@PathVariable String busId) {
        return routeRepository.findByBusId(busId);
    }

    // ✅ Update route
    @PutMapping("/update/{id}")
    public Route updateRoute(@PathVariable String id, @RequestBody Route updatedRoute) {
        Optional<Route> existing = routeRepository.findById(id);
        if (existing.isPresent()) {
            Route route = existing.get();
            route.setFrom(updatedRoute.getFrom());
            route.setTo(updatedRoute.getTo());
            route.setStops(updatedRoute.getStops());
            route.setTimings(updatedRoute.getTimings());
            return routeRepository.save(route);
        }
        return null;
    }

    // ✅ Delete route
    @DeleteMapping("/delete/{id}")
    public void deleteRoute(@PathVariable String id) {
        routeRepository.deleteById(id);
    }
}
