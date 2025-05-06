package org.example.learningsystem.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record LessonResponseDto(
        UUID id,
        @Schema(pattern = ".*[^\\s]+.*")
        String title,
        @Schema(minimum = "0")
        Integer duration,
        UUID courseId
) {
}
