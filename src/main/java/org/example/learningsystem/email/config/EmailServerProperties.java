package org.example.learningsystem.email.config;

/**
 * Interface provides access to the email server properties.
 */
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
