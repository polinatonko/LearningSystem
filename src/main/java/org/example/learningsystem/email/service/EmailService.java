package org.example.learningsystem.email.service;

import org.example.learningsystem.email.config.EmailServerProperties;

/**
 * Interface for sending email messages through an SMTP server.
 * <p>
 * Implementations should use the provided {@link EmailServerProperties} to configure the SMTP connection
 * and deliver the message to the specified recipient.
 */
public interface EmailService {

    /**
     * Sends email using the specified SMTP server properties.
     *
     * @param to               the email address of the recipient
     * @param subject          the subject line of the email
     * @param text             the body content of the email
     * @param serverProperties the SMTP server configuration to use for sending
     */
    void send(String to, String subject, String text, EmailServerProperties serverProperties);
}
