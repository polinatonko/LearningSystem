package org.example.learningsystem.util;

import org.example.learningsystem.lesson.dto.classroom.ClassroomLessonRequestDto;
import org.example.learningsystem.lesson.dto.lesson.LessonRequestDto;
import org.example.learningsystem.lesson.dto.video.VideoLessonRequestDto;

public class LessonRequestDtoUtils {

    public static final String TITLE = "Lesson title";
    public static final Integer DURATION = 60;
    public static final String URL = "https://google.com";
    public static final String PLATFORM = "Google Meet";
    public static final String LOCATION = "Minsk";
    public static final Integer CAPACITY = 10;
    public static final String VIDEO_TYPE = "VIDEO";
    public static final String CLASSROOM_TYPE = "CLASSROOM";

    public static LessonRequestDto createVideoLessonRequestDto() {
        return VideoLessonRequestDto.builder()
                .title(TITLE)
                .duration(DURATION)
                .type(VIDEO_TYPE)
                .url(URL)
                .platform(PLATFORM)
                .build();
    }

    public static ClassroomLessonRequestDto createClassroomLessonRequestDto() {
        return ClassroomLessonRequestDto.builder()
                .title(TITLE)
                .duration(DURATION)
                .type(CLASSROOM_TYPE)
                .location(LOCATION)
                .capacity(CAPACITY)
                .build();
    }

}
