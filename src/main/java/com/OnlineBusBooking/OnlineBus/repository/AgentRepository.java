package com.OnlineBusBooking.OnlineBus.repository;

import com.OnlineBusBooking.OnlineBus.model.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends MongoRepository<Agent, String> {
    Optional<Agent> findByEmail(String email);
    List<Agent> findAllByRole(String role); // Filter agents only
}
