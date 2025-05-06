package org.example.learningsystem.lesson.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record LessonRequestDto(
        UUID id,
        @NotBlank
        String title,
        @Min(0)
        Integer duration,
        UUID courseId
) {
}