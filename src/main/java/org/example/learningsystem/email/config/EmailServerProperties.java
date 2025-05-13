package org.example.learningsystem.email.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
public class EmailServerProperties {

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
    private String startTls;
}