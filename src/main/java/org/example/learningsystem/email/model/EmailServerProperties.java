package org.example.learningsystem.email.model;

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
