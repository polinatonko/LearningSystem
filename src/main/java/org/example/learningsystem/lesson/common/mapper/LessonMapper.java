package org.example.learningsystem.lesson.common.mapper;

import org.example.learningsystem.lesson.classroom.dto.ClassroomLessonRequestDto;
import org.example.learningsystem.lesson.classroom.dto.ClassroomLessonResponseDto;
import org.example.learningsystem.lesson.video.dto.VideoLessonRequestDto;
import org.example.learningsystem.lesson.video.dto.VideoLessonResponseDto;
import org.example.learningsystem.lesson.classroom.model.ClassroomLesson;
import org.example.learningsystem.lesson.common.model.Lesson;
import org.example.learningsystem.lesson.common.dto.LessonRequestDto;
import org.example.learningsystem.lesson.common.dto.LessonResponseDto;
import org.example.learningsystem.lesson.video.model.VideoLesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;

import static org.example.learningsystem.lesson.common.constant.LessonTypeConstants.CLASSROOM;
import static org.example.learningsystem.lesson.common.constant.LessonTypeConstants.VIDEO;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LessonMapper {

    @SubclassMapping(source = ClassroomLesson.class, target = ClassroomLessonResponseDto.class)
    @SubclassMapping(source = VideoLesson.class, target = VideoLessonResponseDto.class)
    @Mapping(target = "courseId", source = "course.id")
    LessonResponseDto toDto(Lesson lesson);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "type", constant = CLASSROOM)
    ClassroomLessonResponseDto toDto(ClassroomLesson classroomLesson);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "type", constant = VIDEO)
    VideoLessonResponseDto toDto(VideoLesson videoLesson);

    @SubclassMapping(source = ClassroomLessonRequestDto.class, target = ClassroomLesson.class)
    @SubclassMapping(source = VideoLessonRequestDto.class, target = VideoLesson.class)
    @Mapping(target = "course.id", source = "courseId")
    Lesson toEntity(LessonRequestDto lessonRequestDto);

    ClassroomLesson toEntity(ClassroomLessonRequestDto classroomLessonRequestDto);

    VideoLesson toEntity(VideoLessonRequestDto videoLessonRequestDto);
}
