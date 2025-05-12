package org.example.learningsystem.email.service;

import org.example.learningsystem.email.config.EmailServerProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailServiceImpl implements EmailService {

    public void send(String to, String subject, String text, EmailServerProperties serverProperties) {
        var sender = getSender(serverProperties);
        var message = buildMessage(to, serverProperties.getFrom(), subject, text);
        sender.send(message);
    }

    private JavaMailSender getSender(EmailServerProperties serverProperties) {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(serverProperties.getHost());
        mailSender.setPort(Integer.parseInt(serverProperties.getPort()));
        mailSender.setUsername(serverProperties.getUser());
        mailSender.setPassword(serverProperties.getPassword());

        var mailProperties = mailSender.getJavaMailProperties();
        fillMailProperties(mailProperties, serverProperties);

        return mailSender;
    }

    private void fillMailProperties(Properties properties, EmailServerProperties serverProperties) {
        properties.put("mail.transport.protocol", serverProperties.getProtocol());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
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