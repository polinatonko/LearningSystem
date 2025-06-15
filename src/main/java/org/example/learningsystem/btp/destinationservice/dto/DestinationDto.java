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

    private OwnerDto owner;

    private DestinationConfigurationDto destinationConfiguration;
}
