package com.OnlineBusBooking.OnlineBus.service;

import com.OnlineBusBooking.OnlineBus.model.Agent;
import com.OnlineBusBooking.OnlineBus.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean emailExists(String email) {
        return agentRepository.findByEmail(email).isPresent();
    }

    public Agent saveAgent(Agent agent) {
        agent.setPassword(passwordEncoder.encode(agent.getPassword()));
        agent.setRole("agent");
        return agentRepository.save(agent);
    }

    public List<Agent> getAllAgents() {
        return agentRepository.findAllByRole("agent");
    }

    // ✅ Add this method for login
    public Optional<Agent> findByEmail(String email) {
        return agentRepository.findByEmail(email);
    }
}
