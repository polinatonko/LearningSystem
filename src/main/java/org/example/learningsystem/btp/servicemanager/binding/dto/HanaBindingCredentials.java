package org.example.learningsystem.btp.servicemanager.binding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.core.db.config.CustomDataSourceProperties;

/**
 * Represents the credentials for an SAP HANA Database schema binding.
 */
@Getter
@Setter
public class HanaBindingCredentials implements CustomDataSourceProperties {

    /**
     * The JDBC URL for connecting to the schema.
     */
    private String url;

    /**
     * The username for database authentication.
     */
    @JsonProperty("user")
    private String username;

    /**
     * The password for the database authentication.
     */
    private String password;

    /**
     * The name of the schema.
     */
    private String schema;
}
