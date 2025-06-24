package com.OnlineBusBooking.OnlineBus.repository;

import com.OnlineBusBooking.OnlineBus.model.Staff;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StaffRepository extends MongoRepository<Staff, String> {
    List<Staff> findByBusId(String busId);
}
