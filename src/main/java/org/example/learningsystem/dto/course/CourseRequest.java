package org.example.learningsystem.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CourseRequest(
        UUID id,
        String title,
        String description,
        BigDecimal price,
        BigDecimal coinsPaid,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDateTime startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDateTime endDate,
        Boolean isPublic
) {}