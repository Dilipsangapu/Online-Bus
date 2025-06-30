package com.OnlineBusBooking.OnlineBus.service;

import com.OnlineBusBooking.OnlineBus.model.Route;
import com.OnlineBusBooking.OnlineBus.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public List<Route> getRoutesByBusId(String busId) {
        return routeRepository.findByBusId(busId);
    }
    public List<Route> findByFromAndTo(String from, String to) {
        return routeRepository.findByFromIgnoreCaseAndToIgnoreCase(from, to);
    }

}
