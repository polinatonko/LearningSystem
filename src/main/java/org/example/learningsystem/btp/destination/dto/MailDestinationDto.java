package org.example.learningsystem.btp.destination.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.email.config.EmailServerProperties;

@Getter
@Setter
public class MailDestinationDto extends DestinationDto implements EmailServerProperties {

    @NotNull
    @JsonProperty("mail.smtp.from")
    private String from;
    @NotNull
    @JsonProperty("mail.user")
    private String user;
    @NotNull
    @JsonProperty("mail.password")
    private String password;
    @NotNull
    @JsonProperty("mail.smtp.host")
    private String host;
    @NotNull
    @JsonProperty("mail.smtp.port")
    private String port;
    @NotNull
    @JsonProperty("mail.transport.protocol")
    private String protocol;
    @NotNull
    @JsonProperty("mail.smtp.auth")
    private String auth;
    @NotNull
    @JsonProperty("mail.smtp.starttls.enable")
    private String startTlsEnable;
}
