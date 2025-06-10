package org.example.learningsystem.btp.servicemanager.instance.dto;

import java.util.UUID;

public record ServiceInstanceResponseDto(UUID id, String name, String servicePlanId) {
}
