package com.handson.springresthandson.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Holds the UserDetailsService/PasswordEncoder beans separately from
 * SecurityConfig. Keeping them out of SecurityConfig avoids a circular
 * dependency: JwtAuthFilter needs UserDetailsService, and SecurityConfig
 * needs JwtAuthFilter, so UserDetailsService can't also live inside
 * SecurityConfig.
 * <p>
 * Demo user: username "user", password "password" (BCrypt-encoded).
 * In a real app this would come from a database-backed UserDetailsService.
 */
@Configuration
public class UserConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password(encoder.encode("password"))
                        .roles("USER")
                        .build()
        );
    }
}
