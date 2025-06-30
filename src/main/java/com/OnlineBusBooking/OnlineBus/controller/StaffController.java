package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.Staff;
import com.OnlineBusBooking.OnlineBus.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @PostMapping("/add")
    public Staff addStaff(@RequestBody Staff staff) {
        return staffRepository.save(staff);
    }

    @GetMapping("/by-bus/{busId}")
    public List<Staff> getStaffByBus(@PathVariable String busId) {
        return staffRepository.findByBusId(busId);
    }

    @PutMapping("/update/{id}")
    public Staff updateStaff(@PathVariable String id, @RequestBody Staff updated) {
        Optional<Staff> existing = staffRepository.findById(id);
        if (existing.isPresent()) {
            Staff staff = existing.get();
            staff.setDriverName(updated.getDriverName());
            staff.setDriverContact(updated.getDriverContact());
            staff.setConductorName(updated.getConductorName());
            staff.setConductorContact(updated.getConductorContact());
            return staffRepository.save(staff);
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStaff(@PathVariable String id) {
        staffRepository.deleteById(id);
    }
}
