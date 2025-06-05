package org.example.learningsystem.btp.saapprovisioningservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class ServiceDependenciesProperties {

    private List<String> dependencies;
}
