package org.example.learningsystem.service.email;

public interface EmailService {
    void send(String to, String subject, String text);
}
