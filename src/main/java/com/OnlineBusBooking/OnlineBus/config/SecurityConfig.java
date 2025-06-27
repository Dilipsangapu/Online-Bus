package com.OnlineBusBooking.OnlineBus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/register", "/login", "/dashboard",
                                "/admin-dashboard", "/agent-dashboard", "/process-login",
                                "/css/**", "/js/**", "/images/**", "/fonts/**",
                                "/buses/update/**","/buses/edit/**",
                                // ✅ Existing permitted APIs
                                "/api/auth/**", "/api/agents/**", "/api/buses/**",

                                // ✅ NEW: Allow access to /buses/api/**
                                "/buses/api/**",

                                // ✅ Thymeleaf views and agent tools
                                "/edit-bus/**", "/agent/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/agent-dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
