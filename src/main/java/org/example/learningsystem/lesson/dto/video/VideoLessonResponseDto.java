package org.example.learningsystem.lesson.dto.video;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.lesson.dto.lesson.LessonResponseDto;

@Schema
@Getter
@Setter
public class VideoLessonResponseDto extends LessonResponseDto {

    @Schema(pattern = ".*[^\\s]+.*")
    private String url;
    private String platform;
}
