package org.example.learningsystem.btp.destinationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an instance of the destination.
 */
@AllArgsConstructor
@Getter
@Setter
public class DestinationDto {

    /**
     * Owner of the destination.
     */
    private OwnerDto owner;

    /**
     * Configuration properties of the destination.
     */
    private DestinationConfigurationDto destinationConfiguration;
}
