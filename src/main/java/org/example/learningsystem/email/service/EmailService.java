package org.example.learningsystem.email.service;

public interface EmailService {

    void send(String to, String subject, String text);
}
