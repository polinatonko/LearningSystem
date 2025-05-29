package org.example.learningsystem.lesson.dto.classroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.lesson.dto.lesson.LessonResponseDto;

@Schema
@Getter
@Setter
public class ClassroomLessonResponseDto extends LessonResponseDto {

    @Schema(pattern = ".*[^\\s]+.*")
    private String location;

    @Schema(minimum = "1")
    private Integer capacity;
}