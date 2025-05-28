package org.example.learningsystem.lesson.dto.lesson;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.learningsystem.lesson.dto.classroom.ClassroomLessonRequestDto;
import org.example.learningsystem.lesson.dto.video.VideoLessonRequestDto;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassroomLessonRequestDto.class, name = "CLASSROOM"),
        @JsonSubTypes.Type(value = VideoLessonRequestDto.class, name = "VIDEO")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class LessonRequestDto {

    private UUID id;
    @NotBlank
    private String title;
    @Min(0)
    private Integer duration;
    private UUID courseId;
    private String type;
}