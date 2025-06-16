package org.example.learningsystem.btp.servicemanager.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

/**
 * Represents a paginated response from the Service Manager API.
 *
 * @param numItems the total number of items
 * @param items    the list of items
 * @param <T>      the type of items contained in the response
 */
@JsonNaming(SnakeCaseStrategy.class)
public record PaginatedResponseDto<T>(
        Integer numItems,
        List<T> items
) {
}
