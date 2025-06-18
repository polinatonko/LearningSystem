package org.example.learningsystem.btp.xsuaa.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for the XSUAA service binding.
 * <p>
 * Contains credentials and endpoint information required to authenticate with the XSUAA service.
 */
@Component
@ConfigurationProperties(prefix = "btp.services.xsuaa")
@Getter
@Setter
public class XsuaaProperties {

    private String tokenUrl;

    private String clientId;

    private String clientSecret;
}
