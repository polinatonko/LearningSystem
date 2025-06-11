package org.example.learningsystem.btp.saasprovisioningservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for the XSUAA service binding.
 * <p>
 * Contains credentials and endpoint information required to authenticate with the XSUAA service.
 */
@Component
@ConfigurationProperties(prefix = "sap.security.services.xsuaa")
@Getter
@Setter
public class XSUAAProperties {

    @Name("url")
    private String tokenUrl;

    @Name("clientid")
    private String clientId;

    @Name("clientsecret")
    private String clientSecret;
}
