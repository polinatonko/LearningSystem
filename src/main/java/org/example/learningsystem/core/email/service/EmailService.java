package org.example.learningsystem.core.email.service;

import org.example.learningsystem.core.email.config.EmailServerProperties;

public interface EmailService {

    void send(String to, String subject, String text, EmailServerProperties serverProperties);
}
