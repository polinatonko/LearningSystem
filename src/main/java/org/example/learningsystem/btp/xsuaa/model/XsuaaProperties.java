package org.example.learningsystem.btp.xsuaa.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for the XSUAA service binding.
 * <p>
 * Contains credentials and endpoint information required to authenticate with the XSUAA service.
 */
@Component
@ConfigurationProperties(prefix = "btp.services.xsuaa")
@Profile("cloud")
@Getter
@Setter
public class XsuaaProperties {

    /**
     * URL for obtaining tokens.
     */
    @NotNull
    private String tokenUrl;

    /**
     * client_id for the XSUAA Service.
     */
    @NotNull
    private String clientId;

    /**
     * client_secret for the XSUAA Service.
     */
    @NotNull
    private String clientSecret;
}
