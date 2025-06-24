package com.OnlineBusBooking.OnlineBus.service;

import com.OnlineBusBooking.OnlineBus.model.User;
import com.OnlineBusBooking.OnlineBus.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ This runs once when the app starts
    @PostConstruct
    public void init() {
        insertDefaultAdmin(); // Only admin
    }

    // ✅ Insert admin only once
    private void insertDefaultAdmin() {
        if (userRepository.findByEmail("admin@onlinebus.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@onlinebus.com");
            admin.setPhone("9999999999");
            admin.setAge(35);
            admin.setGender("Male");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("admin");
            userRepository.save(admin);
            System.out.println("✅ Admin inserted");
        } else {
            System.out.println("⚠️ Admin already exists. Skipping...");
        }
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean phoneExists(String phone) {
        return userRepository.findByPhone(phone).isPresent();
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("user");
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(null);
    }
}
