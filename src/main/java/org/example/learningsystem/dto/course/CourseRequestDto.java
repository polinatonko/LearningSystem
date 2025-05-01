package org.example.learningsystem.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CourseRequestDto(
        UUID id,
        @NotBlank
        String title,
        String description,
        @Min(0)
        BigDecimal price,
        @Min(0)
        BigDecimal coinsPaid,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
        LocalDateTime startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
        LocalDateTime endDate,
        Boolean isPublic
) {}