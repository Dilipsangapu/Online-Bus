package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.Bus;
import com.OnlineBusBooking.OnlineBus.model.Route;
import com.OnlineBusBooking.OnlineBus.service.BusService;
import com.OnlineBusBooking.OnlineBus.service.RouteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/buses")
public class BusController {

    @Autowired
    private BusService busService;

    // ✅ Edit Bus Page (Used by Thymeleaf view)
    @GetMapping("/edit/{id}")
    public String showEditBusForm(@PathVariable String id, Model model) {
        Optional<Bus> optionalBus = busService.getBusById(id);
        if (optionalBus.isPresent()) {
            model.addAttribute("bus", optionalBus.get());
            return "edit-bus"; // Template to render edit form
        } else {
            return "redirect:/agent-dashboard";
        }
    }

    // ✅ Update bus via form (Thymeleaf)
    @PostMapping("/update/{id}")
    public String updateBus(@PathVariable String id, @ModelAttribute("bus") Bus updatedBus) {
        Optional<Bus> optionalBus = busService.getBusById(id);
        if (optionalBus.isEmpty()) {
            return "redirect:/agent-dashboard";
        }

        Bus existingBus = optionalBus.get();
        updatedBus.setId(id);
        updatedBus.setOperatorId(existingBus.getOperatorId());
        updatedBus.setOperatorName(existingBus.getOperatorName());
        updatedBus.setTotalSeats(updatedBus.getSleeperCount() + updatedBus.getSeaterCount());
        updatedBus.setHasUpperDeck("Upper + Lower".equals(updatedBus.getDeckType()));
        updatedBus.setHasLowerDeck(true);

        busService.saveBus(updatedBus);
        return "redirect:/agent-dashboard";
    }

    // ✅ REST: Add bus via fetch (JavaScript)
    @ResponseBody
    @PostMapping("/api/add")
    public String addBus(@RequestBody Bus bus) {
        if (bus.getOperatorId() == null || bus.getOperatorName() == null) {
            return "❌ Operator ID or name missing";
        }

        bus.setTotalSeats(bus.getSleeperCount() + bus.getSeaterCount());
        bus.setHasUpperDeck("Upper + Lower".equals(bus.getDeckType()));
        bus.setHasLowerDeck(true);

        busService.saveBus(bus);
        return "✅ Bus added successfully";
    }

    // ✅ REST: Get buses by operator ID from session (used in JS)
    @ResponseBody
    @GetMapping("/api/session")
    public List<Bus> getBusesFromSession(HttpSession session) {
        String operatorId = (String) session.getAttribute("email");
        if (operatorId == null || operatorId.isEmpty()) {
            return List.of(); // empty list if session invalid
        }
        return busService.getBusesByOperator(operatorId);
    }

    // ✅ REST: Fallback if operatorId is passed manually
    @ResponseBody
    @GetMapping("/api/by-operator/{operatorId}")
    public List<Bus> getBusesByOperatorId(@PathVariable String operatorId) {
        return busService.getBusesByOperator(operatorId);
    }
    @Autowired
    private RouteService routeService;

    @GetMapping("/api/search")
    @ResponseBody
    public List<Bus> searchBuses(@RequestParam String from, @RequestParam String to, @RequestParam String date) {
        // Step 1: Get matching routes
        List<Route> routes = routeService.findByFromAndTo(from, to);

        // Step 2: Extract busIds
        List<String> busIds = routes.stream()
                .map(Route::getBusId)
                .toList();

        // Step 3: Get buses by IDs
        return busService.getBusesByIds(busIds);
    }

}
