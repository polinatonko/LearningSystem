package org.example.learningsystem.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Schema
@Getter
@Setter
public class LessonResponseDto {

    private UUID id;

    @Schema(pattern = ".*[^\\s]+.*")
    private String title;

    @Schema(minimum = "0")
    private Integer duration;

    private UUID courseId;

    private String type;

    private Instant created;

    private String createdBy;

    private Instant lastChanged;

    private String lastChangedBy;
}
