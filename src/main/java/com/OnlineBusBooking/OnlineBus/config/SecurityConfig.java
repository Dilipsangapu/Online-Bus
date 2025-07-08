package com.OnlineBusBooking.OnlineBus.config;

<<<<<<< HEAD
=======
import com.OnlineBusBooking.OnlineBus.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> aa3dc81 (updated)
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
<<<<<<< HEAD
=======
import org.springframework.security.core.authority.AuthorityUtils;
>>>>>>> aa3dc81 (updated)

@Configuration
@EnableWebSecurity
public class SecurityConfig {

<<<<<<< HEAD
=======
    @Autowired
    private CustomUserDetailsService userDetailsService;

>>>>>>> aa3dc81 (updated)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
<<<<<<< HEAD
=======
                .userDetailsService(userDetailsService) // ✅ Use custom user loader
>>>>>>> aa3dc81 (updated)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/register", "/login", "/dashboard",
                                "/admin-dashboard", "/agent-dashboard", "/process-login",
                                "/css/**", "/js/**", "/images/**", "/fonts/**",
                                "/buses/update/**", "/buses/edit/**",
<<<<<<< HEAD
                                "/buses/api/search", // ✅ <--- ADD THIS LINE EXPLICITLY
=======
                                "/buses/api/search",
>>>>>>> aa3dc81 (updated)
                                "/api/auth/**", "/api/agents/**", "/api/buses/**",
                                "/buses/api/**",
                                "/api/routes/**",
                                "/api/search-buses",
                                "/api/seats/**",
                                "/api/schedule/**",
                                "/api/**",
                                "/edit-bus/**", "/agent/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
<<<<<<< HEAD

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/agent-dashboard", true)
=======
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            var roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                            String email = authentication.getName();
                            request.getSession().setAttribute("email", email);
                            request.getSession().setAttribute("name", email); // 🔁 Replace with actual name if needed

                            // 🔍 Debug (optional): Print role
                            System.out.println("Logged in as: " + email + ", roles: " + roles);

                            if (roles.contains("ADMIN")) {
                                response.sendRedirect("/admin/dashboard");
                            } else if (roles.contains("AGENT")) {
                                response.sendRedirect("/agent/dashboard");
                            } else if (roles.contains("USER")) {
                                response.sendRedirect("/user/dashboard");
                            } else {
                                response.sendRedirect("/login?error=unauthorized");
                            }
                        })
>>>>>>> aa3dc81 (updated)
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
