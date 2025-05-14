package org.example.learningsystem.btp.destination.service;

import org.example.learningsystem.btp.destination.dto.DestinationDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("cloud")
public class DestinationServiceLocalImpl implements DestinationService {

    @Override
    public DestinationDto getDestinationByName(String name) {
        return null;
    }

}
