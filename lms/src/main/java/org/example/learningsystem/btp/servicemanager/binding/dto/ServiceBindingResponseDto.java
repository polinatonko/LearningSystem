package org.example.learningsystem.btp.servicemanager.binding.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents the response containing details of a service binding from SAP BTP.
 *
 * @param id                the id of the binding
 * @param name              the name of the binding
 * @param serviceInstanceId the id of the service instance this binding is associated with
 * @param credentials       the {@link HanaBindingCredentials} with database connection details
 * @param labels             a map of labels associated with the binding
 */
public record ServiceBindingResponseDto(

        UUID id,

        String name,

        String serviceInstanceId,

        HanaBindingCredentials credentials,

        Map<String, List<String>> labels
) {
}
