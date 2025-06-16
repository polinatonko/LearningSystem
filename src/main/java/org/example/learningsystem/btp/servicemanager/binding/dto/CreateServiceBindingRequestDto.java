package org.example.learningsystem.btp.servicemanager.binding.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

/**
 * Represents a request for creating a service binding in SAP BTP.
 *
 * @param name              the unique name of the binding
 * @param serviceInstanceId the UUID of the service instance to which this binding will be attached
 * @param labels            a map of labels associated with the binding
 */
@JsonNaming(SnakeCaseStrategy.class)
public record CreateServiceBindingRequestDto(

        String name,

        UUID serviceInstanceId,

        Map<String, List<Object>> labels
) {
}
