package org.example.learningsystem.btp.saasprovisioningservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class ServiceDependencies {

    private List<String> dependencies;
}
