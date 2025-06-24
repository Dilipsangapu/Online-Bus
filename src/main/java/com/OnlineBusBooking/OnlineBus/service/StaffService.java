package com.OnlineBusBooking.OnlineBus.service;

import com.OnlineBusBooking.OnlineBus.model.Staff;
import com.OnlineBusBooking.OnlineBus.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffRepository repo;

    public Staff saveStaff(Staff staff) {
        return repo.save(staff);
    }

    public List<Staff> getStaffByBusId(String busId) {
        return repo.findByBusId(busId);
    }
}
