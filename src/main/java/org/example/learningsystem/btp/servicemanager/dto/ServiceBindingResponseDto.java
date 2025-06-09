package org.example.learningsystem.btp.servicemanager.dto;

import java.util.Map;
import java.util.UUID;

public record ServiceBindingResponseDto(
        UUID id,
        String name,
        String serviceInstanceId,
        Map<String, String> credentials
) {
}
