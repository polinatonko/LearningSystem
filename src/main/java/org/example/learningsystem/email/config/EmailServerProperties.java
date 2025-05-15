package org.example.learningsystem.email.config;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.learningsystem.btp.destination.annotation.DestinationProperty;
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
    @DestinationProperty("mail.smtp.from")
    private String from;
    @NotNull
    @DestinationProperty("mail.user")
    private String user;
    @NotNull
    @DestinationProperty("mail.password")
    private String password;
    @NotNull
    @DestinationProperty("mail.smtp.host")
    private String host;
    @NotNull
    @DestinationProperty("mail.smtp.port")
    private String port;
    @NotNull
    @DestinationProperty("mail.transport.protocol")
    private String protocol;
    @NotNull
    @DestinationProperty("mail.smtp.auth")
    private String auth;
    @NotNull
    @DestinationProperty("mail.smtp.starttls.enable")
    private String startTlsEnable;
}
