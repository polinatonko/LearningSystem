package org.example.learningsystem.core.db.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "datasource")
@Getter
@Setter
public class DataSourceConfigurationProperties implements DataSourceProperties {

    private String url;

    private String username;

    private String password;
}
