package org.example.learningsystem.btp.servicemanager.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(SnakeCaseStrategy.class)
public record CreateServiceInstanceByOfferingAndPlanName(String name, String serviceOfferingName,
                                                         String servicePlanName) {
}
