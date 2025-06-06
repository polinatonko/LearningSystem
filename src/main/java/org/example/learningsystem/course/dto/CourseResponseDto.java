package org.example.learningsystem.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.learningsystem.core.util.format.DateFormatUtils.DATE_TIME_FORMAT;

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

        @JsonFormat(pattern = DATE_TIME_FORMAT)
        LocalDateTime startDate,

        @JsonFormat(pattern = DATE_TIME_FORMAT)
        LocalDateTime endDate,

        Boolean isPublic,

        @JsonFormat(pattern = DATE_TIME_FORMAT, timezone = "UTC")
        Instant created,

        String createdBy,

        @JsonFormat(pattern = DATE_TIME_FORMAT, timezone = "UTC")
        Instant lastChanged,

        String lastChangedBy) {
}