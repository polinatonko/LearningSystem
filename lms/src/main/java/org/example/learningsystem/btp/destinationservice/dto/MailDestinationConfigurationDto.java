package org.example.learningsystem.btp.destinationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.email.config.EmailServerProperties;

/**
 * Represents an instance of the destination configuration with SMTP server credentials.
 */
@Getter
@Setter
public class MailDestinationConfigurationDto extends DestinationConfigurationDto implements EmailServerProperties {

    /**
     * Email address of the email sender.
     */
    @NotNull
    @JsonProperty("mail.smtp.from")
    private String from;

    /**
     * Username for accessing the SMTP server.
     */
    @NotNull
    @JsonProperty("mail.user")
    private String user;

    /**
     * Password for accessing the SMTP server.
     */
    @NotNull
    @JsonProperty("mail.password")
    private String password;

    /**
     * Host address of the SMTP server.
     */
    @NotNull
    @JsonProperty("mail.smtp.host")
    private String host;

    /**
     * Port of the SMTP server.
     */
    @NotNull
    @JsonProperty("mail.smtp.port")
    private String port;

    /**
     * Name of the mail transport protocol.
     */
    @NotNull
    @JsonProperty("mail.transport.protocol")
    private String protocol;

    /**
     * Flag indicating whether SMTP authentication is enabled.
     */
    @NotNull
    @JsonProperty("mail.smtp.auth")
    private String auth;

    /**
     * Flag indicating whether STARTTLS is enabled.
     */
    @NotNull
    @JsonProperty("mail.smtp.starttls.enable")
    private String startTlsEnable;
}
