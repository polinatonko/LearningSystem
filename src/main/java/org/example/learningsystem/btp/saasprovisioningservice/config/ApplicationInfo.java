package org.example.learningsystem.btp.saasprovisioningservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sap.security.services.xsuaa")
@Getter
@Setter
public class ApplicationInfo {

    @Name("url")
    private String tokenUrl;

    @Name("clientid")
    private String clientId;

    @Name("clientsecret")
    private String clientSecret;
}
