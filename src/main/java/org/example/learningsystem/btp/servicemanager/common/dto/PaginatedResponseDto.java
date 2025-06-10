package org.example.learningsystem.btp.servicemanager.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(SnakeCaseStrategy.class)
public record PaginatedResponseDto<T>(
        Integer numItems,
        List<T> items
) {
}
