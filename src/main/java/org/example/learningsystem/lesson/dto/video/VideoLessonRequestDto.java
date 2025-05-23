package org.example.learningsystem.lesson.dto.video;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.lesson.dto.lesson.LessonRequestDto;

@Getter
@Setter
public class VideoLessonRequestDto extends LessonRequestDto {
    @NotBlank
    @Size(max = 256)
    private String url;
    @Size(max = 100)
    private String platform;
}
