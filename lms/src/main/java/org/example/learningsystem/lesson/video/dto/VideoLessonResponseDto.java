package org.example.learningsystem.lesson.video.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.example.learningsystem.lesson.common.dto.LessonResponseDto;

@Schema
@Getter
@Setter
public class VideoLessonResponseDto extends LessonResponseDto {

    @Schema(pattern = ".*[^\\s]+.*")
    private String url;

    private String platform;
}
