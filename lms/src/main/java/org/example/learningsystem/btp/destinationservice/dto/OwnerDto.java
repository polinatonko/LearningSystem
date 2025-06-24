package org.example.learningsystem.btp.destinationservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperCamelCaseStrategy;

/**
 * Represents the owner of the destination.
 *
 * @param subaccountId the UUID of the destination subaccount.
 * @param instanceId   the UUID of the destination service.
 */
@JsonNaming(UpperCamelCaseStrategy.class)
public record OwnerDto(UUID subaccountId, UUID instanceId) {
}
