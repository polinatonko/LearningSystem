package org.example.learningsystem.btp.servicemanager.instance.dto;

import java.util.UUID;

/**
 * Represents the response containing details of a service instance.
 *
 * @param id            the id of the instance
 * @param name          the name of the instance
 * @param servicePlanId the id of the service plan associated with this instance
 */
public record ServiceInstanceResponseDto(

        UUID id,

        String name,

        String servicePlanId) {
}
