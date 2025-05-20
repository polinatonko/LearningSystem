package org.example.learningsystem.email.config;

public interface EmailServerProperties {

    String getFrom();

    String getUser();

    String getPassword();

    String getHost();

    String getPort();

    String getProtocol();

    String getAuth();

    String getStartTlsEnable();
}
