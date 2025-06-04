package org.example.learningsystem.lesson.common.builder;

import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.lesson.classroom.dto.ClassroomLessonRequestDto;
import org.example.learningsystem.lesson.video.dto.VideoLessonRequestDto;
import org.example.learningsystem.lesson.classroom.model.ClassroomLesson;
import org.example.learningsystem.lesson.video.model.VideoLesson;

import java.util.UUID;

import static org.example.learningsystem.lesson.common.model.LessonTypeConstants.CLASSROOM;
import static org.example.learningsystem.lesson.common.model.LessonTypeConstants.VIDEO;

public class LessonBuilderIT {

    public static final Integer CAPACITY = 10;
    public static final Integer DURATION = 60;
    public static final String LOCATION = "Minsk";
    public static final String PLATFORM = "Google Meet";
    public static final String TITLE = "Lesson title";
    public static final String URL = "https://google.com";

    public static ClassroomLesson buildClassroomLesson(Course course) {
        return ClassroomLesson.builder()
                .title(TITLE)
                .duration(DURATION)
                .location(LOCATION)
                .capacity(CAPACITY)
                .course(course)
                .build();
    }

    public static VideoLesson buildVideoLesson(Course course) {
        return VideoLesson.builder()
                .title(TITLE)
                .duration(DURATION)
                .url(URL)
                .platform(PLATFORM)
                .course(course)
                .build();
    }

    public static VideoLessonRequestDto createVideoLessonRequestDto(UUID courseId) {
        return VideoLessonRequestDto.builder()
                .title(TITLE)
                .duration(DURATION)
                .type(VIDEO)
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
                .type(VIDEO)
                .url(URL)
                .platform(PLATFORM)
                .courseId(courseId)
                .build();
    }

    public static ClassroomLessonRequestDto createClassroomLessonRequestDto(UUID courseId) {
        return ClassroomLessonRequestDto.builder()
                .title(TITLE)
                .duration(DURATION)
                .type(CLASSROOM)
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
                .type(CLASSROOM)
                .location(LOCATION)
                .capacity(CAPACITY)
                .courseId(courseId)
                .build();
    }

}
