package org.example.learningsystem.core.email.service;

import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.email.config.EmailServerProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * {@link EmailService} implementation using JavaMail API to send emails through an SMTP server.
 * <p>
 * This implementation creates a new {@link JavaMailSender} instance for each email sent,
 * configured according to the provided {@link EmailServerProperties}.
 */
@Component
@Slf4j
public class EmailServiceImpl implements EmailService {

    /**
     *  JavaMail property key for SMTP transport protocol.
     */
    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";

    /**
     *  JavaMail property key for SMTP authentication flag.
     */
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

    /**
     *  JavaMail property key for SMTP STARTTLS enablement flag.
     */
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    @Override
    public void send(String to, String subject, String text, EmailServerProperties serverProperties) {
        var sender = getSender(serverProperties);
        var message = buildMessage(to, serverProperties.getFrom(), subject, text);
        sender.send(message);
    }

    /**
     * Creates and configures a new {@link JavaMailSender} instance using the specified server properties.
     *
     * @param serverProperties {@link EmailServerProperties} object with SMTP server configuration
     * @return a configured {@link JavaMailSender} instance
     */
    private JavaMailSender getSender(EmailServerProperties serverProperties) {
        var host = serverProperties.getHost();
        var port = serverProperties.getPort();
        var username = serverProperties.getUser();
        var password = serverProperties.getPassword();

        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        var mailProperties = mailSender.getJavaMailProperties();
        fillMailProperties(mailProperties, serverProperties);

        return mailSender;
    }

    /**
     * Configures {@link Properties} object for the SMTP connection.
     *
     * @param properties       the {@link Properties} object to configure
     * @param serverProperties the {@link EmailServerProperties} object with SMTP server properties
     */
    private void fillMailProperties(Properties properties, EmailServerProperties serverProperties) {
        var protocol = serverProperties.getProtocol();
        var auth = serverProperties.getAuth();
        var startTlsEnable = serverProperties.getStartTlsEnable();

        properties.put(MAIL_TRANSPORT_PROTOCOL, protocol);
        properties.put(MAIL_SMTP_AUTH, auth);
        properties.put(MAIL_SMTP_STARTTLS_ENABLE, startTlsEnable);
    }

    /**
     * Creates a new {@link SimpleMailMessage} object with the specified content.
     *
     * @param to      the recipient email address
     * @param from    the sender email address
     * @param subject the subject line of the email
     * @param text    the body content of the email
     * @return a new {@link SimpleMailMessage} object
     */
    private SimpleMailMessage buildMessage(String to, String from, String subject, String text) {
        var message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);

        return message;
    }

}