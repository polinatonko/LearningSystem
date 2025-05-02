package org.example.learningsystem.dto.lesson;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LessonResponseDto(
        @NotNull
        UUID id,
        @NotBlank
        String title,
        @Min(0)
        Integer duration,
        @NotNull
        UUID courseId
) {}
