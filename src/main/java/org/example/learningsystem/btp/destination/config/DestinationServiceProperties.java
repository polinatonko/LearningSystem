package org.example.learningsystem.btp.destination.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "destination-service")
@Component
@Getter
@Setter
@Validated
public class DestinationServiceProperties {

    @NotNull
    private String uri;
    @NotNull
    private String url;
    @NotNull
    private String clientId;
    @NotNull
    private String clientSecret;
}
