package com.OnlineBusBooking.OnlineBus.service;

import com.OnlineBusBooking.OnlineBus.model.Route;
import com.OnlineBusBooking.OnlineBus.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    @Autowired
    private RouteRepository repo;

    public Route saveRoute(Route route) {
        return repo.save(route);
    }

    public List<Route> getRoutesByBusId(String busId) {
        return repo.findByBusId(busId);
    }
}
