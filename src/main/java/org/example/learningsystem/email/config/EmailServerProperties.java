package org.example.learningsystem.email.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("mail")
@Component
@Validated
@Getter
@Setter
public class EmailServerProperties {

    @NotNull
    private String from;
    private String name;
    @NotNull
    private String token;
}