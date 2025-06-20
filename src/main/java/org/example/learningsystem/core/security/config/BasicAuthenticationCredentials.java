package org.example.learningsystem.core.security.config;

import org.example.learningsystem.core.security.authority.UserAuthority;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for basic authentication credentials.
 */
@Component
@ConfigurationProperties(prefix = "basic-authentication")
@Getter
@Setter
@Validated
public class BasicAuthenticationCredentials {

    /**
     * Credentials for user with {@link UserAuthority#MANAGER} authority.
     */
    @Valid
    @NestedConfigurationProperty
    private UserCredentials manager;

    /**
     * Credentials for user with {@link UserAuthority#STUDENT} authority.
     */
    @Valid
    @NestedConfigurationProperty
    private UserCredentials student;
}
