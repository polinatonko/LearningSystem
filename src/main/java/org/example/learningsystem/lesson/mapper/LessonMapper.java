package org.example.learningsystem.lesson.mapper;

import org.example.learningsystem.lesson.model.Lesson;
import org.example.learningsystem.lesson.dto.LessonRequestDto;
import org.example.learningsystem.lesson.dto.LessonResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LessonMapper {

    @Mapping(target = "courseId", source = "course.id")
    LessonResponseDto toDto(Lesson lesson);

    List<LessonResponseDto> toDtos(List<Lesson> lessons);

    @Mapping(target = "course.id", source = "courseId")
    Lesson toEntity(LessonRequestDto lessonRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course.id", source = "courseId",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(LessonRequestDto lessonRequestDto, @MappingTarget Lesson lesson);
}
