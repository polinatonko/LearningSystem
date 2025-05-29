package org.example.learningsystem.lesson.dto.classroom;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.learningsystem.lesson.dto.lesson.LessonRequestDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ClassroomLessonRequestDto extends LessonRequestDto {

    @NotBlank
    @Size(max = 100)
    private String location;

    @Min(1)
    private Integer capacity;
}
