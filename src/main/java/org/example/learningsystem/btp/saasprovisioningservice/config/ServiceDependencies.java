package org.example.learningsystem.btp.saasprovisioningservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuration properties for service dependencies required by the SaaS Provisioning Service.
 * <p>
 * Contains a list of services that the application depends on, which will be used during the subscription
 * callback process.
 */
@Component
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class ServiceDependencies {

    private List<String> dependencies;
}
