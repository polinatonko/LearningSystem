package org.example.learningsystem.btp.servicemanager.binding.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(SnakeCaseStrategy.class)
public record CreateServiceBindingRequestDto(

        String name,

        UUID serviceInstanceId,

        Map<String, List<Object>> labels
) {
}
