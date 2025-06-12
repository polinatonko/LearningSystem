package org.example.learningsystem.btp.servicemanager.binding.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ServiceBindingResponseDto(

        UUID id,

        String name,

        String serviceInstanceId,

        HanaBindingCredentials credentials,

        Map<String, List<String>> labels
) {
}
