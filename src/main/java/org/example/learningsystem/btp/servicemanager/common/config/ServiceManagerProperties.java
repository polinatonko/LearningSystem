package org.example.learningsystem.btp.servicemanager.common.config;

import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.core.web.oauth2.Oauth2ClientCredentials;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Represents configuration properties for the Service Manager.
 * <p>
 * Contains OAuth 2.0 client credentials and service endpoint URLs.
 */
@Component
@ConfigurationProperties(prefix = "service-manager")
@Getter
@Setter
public class ServiceManagerProperties {

    /**
     * OAuth 2.0 client_id for authentication.
     */
    private String clientId;

    /**
     * OAuth 2.0 client_secret for authentication.
     */
    private String clientSecret;

    /**
     * URL for obtaining OAuth 2.0 tokens.
     */
    private String tokenUrl;

    /**
     * Base URL for the Service Manager API.
     */
    private String url;

    /**
     * Converts these properties to an OAuth 2.0 client credentials object.
     *
     * @return {@link Oauth2ClientCredentials} containing the configured values
     */
    public Oauth2ClientCredentials getOauth2ClientCredentials() {
        return new Oauth2ClientCredentials(clientId, clientSecret, tokenUrl);
    }
}
