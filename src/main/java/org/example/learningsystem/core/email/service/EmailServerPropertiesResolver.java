package org.example.learningsystem.core.email.service;

import org.example.learningsystem.core.email.config.EmailServerProperties;

/**
 * Interface for resolving SMTP server configuration properties.
 */
public interface EmailServerPropertiesResolver {

    /**
     * Resolves and returns the SMTP server configuration.
     *
     * @return fully configured {@link EmailServerProperties} instance
     */
    EmailServerProperties resolve();
}
