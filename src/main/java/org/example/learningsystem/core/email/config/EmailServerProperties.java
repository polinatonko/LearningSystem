package org.example.learningsystem.core.email.config;

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
