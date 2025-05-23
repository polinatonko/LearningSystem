package org.example.learningsystem.lesson.dto.classroom;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.lesson.dto.lesson.LessonRequestDto;

@Getter
@Setter
public class ClassroomLessonRequestDto extends LessonRequestDto {

    @NotBlank
    @Size(max = 100)
    private String location;
    @Min(1)
    private Integer capacity;
}
