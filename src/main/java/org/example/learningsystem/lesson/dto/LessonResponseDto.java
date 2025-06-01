package org.example.learningsystem.lesson.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

import static org.example.learningsystem.core.util.format.DataFormatUtils.DATE_TIME_FORMAT;

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

    @JsonFormat(pattern = DATE_TIME_FORMAT, timezone = "UTC")
    private Instant created;

    private String createdBy;

    @JsonFormat(pattern = DATE_TIME_FORMAT, timezone = "UTC")
    private Instant lastChanged;

    private String lastChangedBy;
}
