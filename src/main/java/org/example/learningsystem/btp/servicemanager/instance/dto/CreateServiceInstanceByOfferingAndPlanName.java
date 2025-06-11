package org.example.learningsystem.btp.servicemanager.instance.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(SnakeCaseStrategy.class)
public record CreateServiceInstanceByOfferingAndPlanName(String name, String serviceOfferingName,
                                                         String servicePlanName,
                                                         Map<String, String> parameters) {
}
