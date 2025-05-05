package org.example.learningsystem.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema
public record CourseResponseDto(
        UUID id,
        @Schema(pattern = ".*[^\\s]+.*")
        String title,
        @Schema(nullable = true)
        String description,
        @Schema(minimum = "0")
        BigDecimal price,
        @Schema(minimum = "0")
        BigDecimal coinsPaid,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean isPublic
) {
}