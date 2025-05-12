package org.example.learningsystem.core.destination.service;

import org.example.learningsystem.email.config.EmailServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!cloud")
public class DestinationServiceLocalImpl implements DestinationService {

    @Override
    public EmailServerProperties getEmailServerProperties() {
        return null;
    }

}
