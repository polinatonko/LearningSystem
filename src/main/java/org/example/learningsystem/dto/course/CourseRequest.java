package org.example.learningsystem.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CourseRequest(
        UUID id,
        @NotBlank
        String title,
        String description,
        @Min(0)
        BigDecimal price,
        @Min(0)
        BigDecimal coinsPaid,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDateTime startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDateTime endDate,
        Boolean isPublic
) {}