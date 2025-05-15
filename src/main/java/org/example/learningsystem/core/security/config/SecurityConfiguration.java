package org.example.learningsystem.core.security.config;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.security.role.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@RequiredArgsConstructor
@Profile("!cloud")
public class SecurityConfiguration {

    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/actuator/**")
                                .hasRole(UserRole.MANAGER.name())
                                .anyRequest()
                                .authenticated())
                .userDetailsService(userDetailsService())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(exceptionHandler ->
                        exceptionHandler
                                .accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(authenticationEntryPoint))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var managerDetails = User.withUsername("manager@gmail.com")
                .password(passwordEncoder().encode("manager"))
                .roles(UserRole.MANAGER.name())
                .build();
        var studentDetails = User.withUsername("student1@gmail.com")
                .password(passwordEncoder().encode("student1"))
                .roles(UserRole.STUDENT.name())
                .build();

        return new InMemoryUserDetailsManager(managerDetails, studentDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
