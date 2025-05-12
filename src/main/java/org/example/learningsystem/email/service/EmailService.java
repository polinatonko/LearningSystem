package org.example.learningsystem.email.service;

import org.example.learningsystem.email.config.EmailServerProperties;

public interface EmailService {

    void send(String to, String subject, String text, EmailServerProperties serverProperties);
}
