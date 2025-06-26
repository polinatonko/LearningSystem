package org.example.learningsystem.course.mapper;

import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.dto.CourseRequestDto;
import org.example.learningsystem.course.dto.CourseResponseDto;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = SPRING)
public interface CourseMapper {

    @Mapping(target = "startDate", source = "course.settings.startDate")
    @Mapping(target = "endDate", source = "course.settings.endDate")
    @Mapping(target = "isPublic", source = "course.settings.isPublic")
    CourseResponseDto toDto(Course course);

    @Mapping(target = "settings.startDate", source = "startDate")
    @Mapping(target = "settings.endDate", source = "endDate")
    @Mapping(target = "settings.isPublic", source = "isPublic", nullValueCheckStrategy = ALWAYS)
    @Mapping(target = "coinsPaid", nullValuePropertyMappingStrategy = IGNORE)
    Course toEntity(CourseRequestDto courseRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "settings.startDate", source = "startDate")
    @Mapping(target = "settings.endDate", source = "endDate")
    @Mapping(target = "settings.isPublic", source = "isPublic", nullValueCheckStrategy = ALWAYS)
    @Mapping(target = "coinsPaid", nullValueCheckStrategy = ALWAYS)
    void toEntity(CourseRequestDto courseRequestDto, @MappingTarget Course course);
}
