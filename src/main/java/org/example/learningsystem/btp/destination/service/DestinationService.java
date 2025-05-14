package org.example.learningsystem.btp.destination.service;

import org.example.learningsystem.btp.destination.dto.DestinationDto;

public interface DestinationService {

    DestinationDto getDestinationByName(String name);
}
