package org.example.learningsystem.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Profile("test")
@Getter
@Setter
@Validated
public class DatasourceProperties {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
