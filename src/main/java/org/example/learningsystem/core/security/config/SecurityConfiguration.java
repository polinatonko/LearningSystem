package org.example.learningsystem.core.security.config;

import org.example.learningsystem.core.security.role.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        var managerDetails = User.withUsername("manager@gmail.com")
                .password(passwordEncoder().encode("manager"))
                .roles(UserRole.MANAGER.toString())
                .build();
        var studentDetails = User.withUsername("student1@gmail.com")
                .password(passwordEncoder().encode("student1"))
                .roles(UserRole.STUDENT.toString())
                .build();

        return new InMemoryUserDetailsManager(managerDetails, studentDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
