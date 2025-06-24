package com.OnlineBusBooking.OnlineBus.controller;

import com.OnlineBusBooking.OnlineBus.model.User;
import com.OnlineBusBooking.OnlineBus.service.AuthService;
import com.OnlineBusBooking.OnlineBus.service.OtpService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OtpService otpService;

    // ✅ Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // ✅ Show register page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // ✅ Register user
    @PostMapping("/api/auth/register")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (authService.emailExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("❌ Email already registered.");
        }
        if (authService.phoneExists(user.getPhone())) {
            return ResponseEntity.badRequest().body("❌ Phone number already registered.");
        }
        authService.registerUser(user);
        return ResponseEntity.ok("✅ User registered successfully.");
    }

    // ✅ Role-based login
    @PostMapping("/process-login")
    public RedirectView processLogin(@RequestParam String email,
                                     @RequestParam String password,
                                     Model model,
                                     HttpSession session) {

        User user = authService.authenticateUser(email, password);

        if (user == null) {
            model.addAttribute("error", "Invalid credentials.");
            return new RedirectView("/login?error");
        }

        // ✅ Store login details in session
        session.setAttribute("email", user.getEmail());
        session.setAttribute("name", user.getName());
        session.setAttribute("role", user.getRole());

        // ✅ Redirect based on role
        return switch (user.getRole()) {
            case "admin" -> new RedirectView("/admin-dashboard");
            case "agent" -> new RedirectView("/agent-dashboard");
            default -> new RedirectView("/dashboard");
        };
    }

    // ✅ User dashboard
    @GetMapping("/dashboard")
    public String showUserDashboard(Model model) {
        model.addAttribute("welcomeMessage", "Welcome to your user dashboard!");
        return "userDashboard";
    }

    // ✅ Admin dashboard
    @GetMapping("/admin-dashboard")
    public String showAdminDashboard() {
        return "adminDashboard";
    }

    // ✅ Agent dashboard
    @GetMapping("/agent-dashboard")
    public String showAgentDashboard() {
        return "agentDashboard";
    }

    // ✅ Send OTP
    @PostMapping("/api/auth/send-otp")
    @ResponseBody
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (otpService.generateAndSendOtp(email)) {
            return ResponseEntity.ok("✅ OTP sent successfully to " + email);
        } else {
            return ResponseEntity.status(500).body("❌ Failed to send OTP. Please try again.");
        }
    }

    // ✅ Verify OTP
    @PostMapping("/api/auth/verify-otp")
    @ResponseBody
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        if (otpService.validateOtp(email, otp)) {
            otpService.clearOtp(email);
            return ResponseEntity.ok("✅ OTP verified successfully.");
        } else {
            return ResponseEntity.status(400).body("❌ Invalid OTP. Please try again.");
        }
    }
}
