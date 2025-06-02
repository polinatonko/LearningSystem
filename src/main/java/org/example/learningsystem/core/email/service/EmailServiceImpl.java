package org.example.learningsystem.core.email.service;

import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.email.config.EmailServerProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    public void send(String to, String subject, String text, EmailServerProperties serverProperties) {
        var sender = getSender(serverProperties);
        var message = buildMessage(to, serverProperties.getFrom(), subject, text);
        sender.send(message);
    }

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

    private void fillMailProperties(Properties properties, EmailServerProperties serverProperties) {
        var protocol = serverProperties.getProtocol();
        var auth = serverProperties.getAuth();
        var startTlsEnable = serverProperties.getStartTlsEnable();

        properties.put(MAIL_TRANSPORT_PROTOCOL, protocol);
        properties.put(MAIL_SMTP_AUTH, auth);
        properties.put(MAIL_SMTP_STARTTLS_ENABLE, startTlsEnable);
    }

    private SimpleMailMessage buildMessage(String to, String from, String subject, String text) {
        var message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);

        return message;
    }

}