package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.Bus;
import com.OnlineBusBooking.OnlineBus.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusService busService;

    // ✅ Add Bus
    @PostMapping("/add")
    public ResponseEntity<String> addBus(@RequestBody Bus bus) {
        if (bus.getOperatorId() == null || bus.getOperatorName() == null) {
            return ResponseEntity.badRequest().body("❌ Operator information missing.");
        }

        busService.saveBus(bus);
        return ResponseEntity.ok("✅ Bus added successfully!");
    }

    // ✅ Get Buses by Operator ID (for layout)
    @GetMapping("/by-operator/{operatorId}")
    public ResponseEntity<List<Bus>> getBusesByOperator(@PathVariable String operatorId) {
        return ResponseEntity.ok(busService.getBusesByOperator(operatorId));
    }

    // ✅ Get Buses by Agent ID (used in JS)
    @GetMapping("/agents/{agentId}/buses")
    public ResponseEntity<List<Bus>> getBusesByAgentId(@PathVariable String agentId) {
        return ResponseEntity.ok(busService.getBusesByOperator(agentId));
    }

    // ✅ Update Bus
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBus(@PathVariable String id, @RequestBody Bus updatedBus) {
        Optional<Bus> optionalBus = busService.getBusById(id);
        if (optionalBus.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Bus not found.");
        }

        Bus existingBus = optionalBus.get();

        updatedBus.setId(id);
        updatedBus.setOperatorId(existingBus.getOperatorId());
        updatedBus.setOperatorName(existingBus.getOperatorName());
        updatedBus.setTotalSeats(updatedBus.getSleeperCount() + updatedBus.getSeaterCount());
        updatedBus.setHasUpperDeck("Upper + Lower".equals(updatedBus.getDeckType()));
        updatedBus.setHasLowerDeck(true);

        busService.saveBus(updatedBus);
        return ResponseEntity.ok("✅ Bus updated successfully!");
    }
}
