package org.example.learningsystem.btp.servicemanager.instance.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

/**
 * Represents a request for creating a service instance in SAP BTP by specifying
 * the service offering and plan names.
 *
 * @param name                the name of the instance
 * @param serviceOfferingName the name of the service offering to use
 * @param servicePlanName     the name of the service plan to use
 * @param parameters          the additional instance parameters
 */
@JsonNaming(SnakeCaseStrategy.class)
public record CreateServiceInstanceByOfferingAndPlanName(

        String name,

        String serviceOfferingName,

        String servicePlanName,

        Map<String, String> parameters) {
}
