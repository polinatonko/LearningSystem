package org.example.learningsystem.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.learningsystem.utils.DataFormatUtils.DATE_TIME_FORMAT;

public record CourseResponseDto(
        @NotNull
        UUID id,
        @NotBlank
        String title,
        String description,
        @Min(0)
        BigDecimal price,
        @NotNull
        @Min(0)
        BigDecimal coinsPaid,
        @JsonFormat(pattern = DATE_TIME_FORMAT)
        LocalDateTime startDate,
        @JsonFormat(pattern = DATE_TIME_FORMAT)
        LocalDateTime endDate,
        @NotNull
        Boolean isPublic
) {}