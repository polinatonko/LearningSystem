package org.example.learningsystem.core.security.config;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "basic-authentication")
@Getter
@Setter
@Validated
public class BasicAuthenticationCredentials {

    @Valid
    @NestedConfigurationProperty
    private UserCredentials manager;

    @Valid
    @NestedConfigurationProperty
    private UserCredentials student;
}
