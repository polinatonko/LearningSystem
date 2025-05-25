package org.example.learningsystem.btp.destination.service;

import org.example.learningsystem.btp.destination.dto.DestinationDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!cloud")
public class LocalDestinationServiceImpl implements DestinationService {

    @Override
    public DestinationDto getByName(String name) {
        return null;
    }

}
