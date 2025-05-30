package org.example.learningsystem.lesson.dto.videolesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.learningsystem.lesson.dto.LessonRequestDto;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class VideoLessonRequestDto extends LessonRequestDto {

    @NotBlank
    @Size(max = 256)
    @URL
    private String url;

    @Size(max = 100)
    private String platform;
}
