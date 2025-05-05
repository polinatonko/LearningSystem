package org.example.learningsystem.mapper;

import org.example.learningsystem.domain.Lesson;
import org.example.learningsystem.dto.lesson.LessonRequestDto;
import org.example.learningsystem.dto.lesson.LessonResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(target = "courseId", source = "course.id")
    LessonResponseDto toDto(Lesson lesson);

    List<LessonResponseDto> toDtos(List<Lesson> lessons);

    @Mapping(target = "course.id", source = "courseId")
    Lesson toEntity(LessonRequestDto lessonRequestDto);

    @Mapping(target = "course.id", source = "courseId")
    void toEntity(LessonRequestDto lessonRequestDto, @MappingTarget Lesson lesson);
}
