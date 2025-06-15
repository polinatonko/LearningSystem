package org.example.learningsystem.btp.destinationservice.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.core.web.oauth2.Oauth2ClientCredentials;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Represents the configuration of the Destination Service.
 */
@ConfigurationProperties(prefix = "destination-service")
@Component
@Profile("cloud")
@Getter
@Setter
@Validated
public class DestinationServiceProperties {

    /**
     * URI of the Destination Service API.
     */
    @NotNull
    private String uri;

    /**
     * URL for obtaining a token for accessing the Destination Service API.
     */
    @NotNull
    private String tokenUrl;

    /**
     * client_id for the Destination Service.
     */
    @NotNull
    private String clientId;

    /**
     * client_secret for the Destination Service.
     */
    @NotNull
    private String clientSecret;

    public Oauth2ClientCredentials getOauth2ClientCredentials() {
        return new Oauth2ClientCredentials(clientId, clientSecret, tokenUrl);
    }
}
