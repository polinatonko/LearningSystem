package org.example.learningsystem.course.mapper;

import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.dto.CourseRequestDto;
import org.example.learningsystem.course.dto.CourseResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CourseMapper {

    @Mapping(target = "startDate", source = "course.settings.startDate")
    @Mapping(target = "endDate", source = "course.settings.endDate")
    @Mapping(target = "isPublic", source = "course.settings.isPublic")
    CourseResponseDto toDto(Course course);

    List<CourseResponseDto> toDtos(List<Course> courses);

    @Mapping(target = "settings.startDate", source = "startDate")
    @Mapping(target = "settings.endDate", source = "endDate")
    @Mapping(target = "settings.isPublic", source = "isPublic", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "coinsPaid", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Course toEntity(CourseRequestDto courseRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "settings.startDate", source = "startDate")
    @Mapping(target = "settings.endDate", source = "endDate")
    @Mapping(target = "settings.isPublic", source = "isPublic", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "coinsPaid", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void toEntity(CourseRequestDto courseRequestDto, @MappingTarget Course course);
}
