package org.example.learningsystem.core.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.example.learningsystem.core.security.authority.UserAuthority.MANAGER;
import static org.example.learningsystem.core.security.authority.UserAuthority.STUDENT;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final BasicAuthenticationCredentials basicAuthCredentialsConfiguration;

    @Bean
    public UserDetailsService userDetailsService() {
        var managerDetails = buildManagerDetails();
        var studentDetails = buildStudentDetails();

        return new InMemoryUserDetailsManager(managerDetails, studentDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetails buildManagerDetails() {
        var managerCredentials = basicAuthCredentialsConfiguration.getManager();

        return User.withUsername(managerCredentials.username())
                .password(passwordEncoder().encode(managerCredentials.password()))
                .authorities(MANAGER.toString())
                .build();
    }

    private UserDetails buildStudentDetails() {
        var studentCredentials = basicAuthCredentialsConfiguration.getStudent();

        return User.withUsername(studentCredentials.username())
                .password(passwordEncoder().encode(studentCredentials.password()))
                .authorities(STUDENT.toString())
                .build();
    }
}
