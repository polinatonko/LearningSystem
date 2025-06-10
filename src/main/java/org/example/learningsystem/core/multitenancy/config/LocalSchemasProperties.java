package org.example.learningsystem.core.multitenancy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "multitenancy")
@Profile("local")
@Getter
@Setter
public class LocalSchemasProperties {

    private List<String> systemSchemas;
}
