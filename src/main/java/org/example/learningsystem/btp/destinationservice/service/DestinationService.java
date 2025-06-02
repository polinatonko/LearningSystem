package org.example.learningsystem.btp.destinationservice.service;

import org.example.learningsystem.btp.destinationservice.dto.DestinationDto;

/**
 * Interface for interacting with the Destination Service API.
 */
public interface DestinationService {

    /**
     * Returns the destination by its name.
     *
     * @param name the name of the destination
     * @return {@link DestinationDto} instance
     */
    DestinationDto getByName(String name);
}
