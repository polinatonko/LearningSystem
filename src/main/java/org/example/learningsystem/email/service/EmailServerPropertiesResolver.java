package org.example.learningsystem.email.service;

import org.example.learningsystem.email.config.EmailServerProperties;

public interface EmailServerPropertiesResolver {

    EmailServerProperties resolve();
}
