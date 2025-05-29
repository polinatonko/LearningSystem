package org.example.learningsystem.lesson.dto.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

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
}
