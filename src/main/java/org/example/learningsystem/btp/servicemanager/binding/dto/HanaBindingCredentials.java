package org.example.learningsystem.btp.servicemanager.binding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.core.db.config.DataSourceProperties;

@Getter
@Setter
public class HanaBindingCredentials implements DataSourceProperties {

    private String url;

    @JsonProperty("user")
    private String username;

    private String password;

    private String schema;
}
