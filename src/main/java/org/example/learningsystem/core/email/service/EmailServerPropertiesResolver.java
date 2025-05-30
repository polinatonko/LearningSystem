package org.example.learningsystem.core.email.service;

import org.example.learningsystem.core.email.config.EmailServerProperties;

public interface EmailServerPropertiesResolver {

    EmailServerProperties resolve();
}
