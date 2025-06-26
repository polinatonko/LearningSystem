package org.example.learningsystem.btp.destinationservice.service;

import org.example.learningsystem.btp.destinationservice.dto.DestinationDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * {@link DestinationService} implementation for the local development and testing.
 */
@Service
@Profile("!cloud")
public class LocalDestinationServiceImpl implements DestinationService {

    @Override
    public DestinationDto getByName(String name) {
        return null;
    }
}
