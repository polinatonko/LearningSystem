package org.example.learningsystem.email.config;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("mail")
@Component
@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailServerPropertiesConfiguration implements EmailServerProperties {

    @NotNull
    private String from;
    @NotNull
    private String user;
    @NotNull
    private String password;
    @NotNull
    private String host;
    @NotNull
    private String port;
    @NotNull
    private String protocol;
    @NotNull
    private String auth;
    @NotNull
    private String startTlsEnable;
}
