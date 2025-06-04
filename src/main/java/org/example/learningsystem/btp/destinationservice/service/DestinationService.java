package org.example.learningsystem.btp.destinationservice.service;

import org.example.learningsystem.btp.destinationservice.dto.DestinationDto;

/**
 * Interface for retrieving destinations as {@link DestinationDto}.
 */
public interface DestinationService {

    /**
     * Retrieves a destination by its name.
     *
     * @param name the name of the destination
     * @return the {@link DestinationDto} instance
     */
    DestinationDto getByName(String name);
}
