package org.example.learningsystem.util;

import org.example.learningsystem.lesson.dto.classroom.ClassroomLessonRequestDto;
import org.example.learningsystem.lesson.dto.video.VideoLessonRequestDto;

import java.util.UUID;

public class LessonTestUtils {

    public static final String TITLE = "Lesson title";
    public static final Integer DURATION = 60;
    public static final String URL = "https://google.com";
    public static final String PLATFORM = "Google Meet";
    public static final String LOCATION = "Minsk";
    public static final Integer CAPACITY = 10;
    public static final String VIDEO_TYPE = "VIDEO";
    public static final String CLASSROOM_TYPE = "CLASSROOM";

    public static VideoLessonRequestDto createVideoLessonRequestDto(UUID courseId) {
        return VideoLessonRequestDto.builder()
                .title(TITLE)
                .duration(DURATION)
                .type(VIDEO_TYPE)
                .url(URL)
                .platform(PLATFORM)
                .courseId(courseId)
                .build();
    }

    public static VideoLessonRequestDto createVideoLessonUpdateRequestDto(UUID id, UUID courseId) {
        return VideoLessonRequestDto.builder()
                .id(id)
                .title(TITLE)
                .duration(DURATION)
                .type(VIDEO_TYPE)
                .url(URL)
                .platform(PLATFORM)
                .courseId(courseId)
                .build();
    }

    public static ClassroomLessonRequestDto createClassroomLessonRequestDto(UUID courseId) {
        return ClassroomLessonRequestDto.builder()
                .title(TITLE)
                .duration(DURATION)
                .type(CLASSROOM_TYPE)
                .location(LOCATION)
                .capacity(CAPACITY)
                .courseId(courseId)
                .build();
    }

    public static ClassroomLessonRequestDto createClassroomLessonUpdateRequestDto(UUID id, UUID courseId) {
        return ClassroomLessonRequestDto.builder()
                .id(id)
                .title(TITLE)
                .duration(DURATION)
                .type(CLASSROOM_TYPE)
                .location(LOCATION)
                .capacity(CAPACITY)
                .courseId(courseId)
                .build();
    }

}
