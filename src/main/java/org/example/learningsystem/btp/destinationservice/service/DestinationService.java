package org.example.learningsystem.btp.destinationservice.service;

import org.example.learningsystem.btp.destinationservice.dto.DestinationDto;

public interface DestinationService {

    DestinationDto getByName(String name);
}
