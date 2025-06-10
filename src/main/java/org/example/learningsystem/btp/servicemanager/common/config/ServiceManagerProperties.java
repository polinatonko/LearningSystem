package org.example.learningsystem.btp.servicemanager.common.config;

import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.core.web.oauth2.Oauth2ClientCredentials;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service-manager")
@Getter
@Setter
public class ServiceManagerProperties implements Oauth2ClientCredentials {

    private String clientId;

    private String clientSecret;

    private String tokenUrl;

    private String url;
}
