package org.example.learningsystem.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.learningsystem.core.util.format.DataFormatUtils.DATE_TIME_FORMAT;

public record CourseRequestDto(
        UUID id,

        @NotBlank
        String title,

        String description,

        @Min(0)
        BigDecimal price,

        @Min(0)
        BigDecimal coinsPaid,

        @JsonFormat(pattern = DATE_TIME_FORMAT)
        @Schema(pattern = "YYYY/MM/dd HH:MM:ss")
        LocalDateTime startDate,

        @JsonFormat(pattern = DATE_TIME_FORMAT)
        @Schema(pattern = "YYYY/MM/dd HH:MM:ss")
        LocalDateTime endDate,

        Boolean isPublic
) {
}