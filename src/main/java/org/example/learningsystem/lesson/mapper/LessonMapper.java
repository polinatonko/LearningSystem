package org.example.learningsystem.lesson.mapper;

import org.example.learningsystem.lesson.dto.classroom.ClassroomLessonRequestDto;
import org.example.learningsystem.lesson.dto.classroom.ClassroomLessonResponseDto;
import org.example.learningsystem.lesson.dto.video.VideoLessonRequestDto;
import org.example.learningsystem.lesson.dto.video.VideoLessonResponseDto;
import org.example.learningsystem.lesson.model.ClassroomLesson;
import org.example.learningsystem.lesson.model.Lesson;
import org.example.learningsystem.lesson.dto.lesson.LessonRequestDto;
import org.example.learningsystem.lesson.dto.lesson.LessonResponseDto;
import org.example.learningsystem.lesson.model.VideoLesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.SubclassMapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LessonMapper {

    @SubclassMapping(source = ClassroomLesson.class, target = ClassroomLessonResponseDto.class)
    @SubclassMapping(source = VideoLesson.class, target = VideoLessonResponseDto.class)
    @Mapping(target = "courseId", source = "course.id")
    LessonResponseDto toDto(Lesson lesson);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "type", constant = "CLASSROOM")
    ClassroomLessonResponseDto toDto(ClassroomLesson classroomLesson);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "type", constant = "VIDEO")
    VideoLessonResponseDto toDto(VideoLesson videoLesson);

    @SubclassMapping(source = ClassroomLessonRequestDto.class, target = ClassroomLesson.class)
    @SubclassMapping(source = VideoLessonRequestDto.class, target = VideoLesson.class)
    @Mapping(target = "course.id", source = "courseId")
    Lesson toEntity(LessonRequestDto lessonRequestDto);

    ClassroomLesson toEntity(ClassroomLessonRequestDto classroomLessonRequestDto);

    VideoLesson toEntity(VideoLessonRequestDto videoLessonRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course.id", source = "courseId",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(LessonRequestDto lessonRequestDto, @MappingTarget Lesson lesson);
}
