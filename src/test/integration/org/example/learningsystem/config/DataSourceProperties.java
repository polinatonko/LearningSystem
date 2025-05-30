package org.example.learningsystem.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "spring.datasource")
@Getter
@Setter
@Validated
public class DataSourceProperties {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
