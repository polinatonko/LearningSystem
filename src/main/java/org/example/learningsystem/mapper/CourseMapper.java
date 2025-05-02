package org.example.learningsystem.mapper;

import org.example.learningsystem.domain.Course;
import org.example.learningsystem.dto.course.CourseRequestDto;
import org.example.learningsystem.dto.course.CourseResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "startDate", source = "course.settings.startDate")
    @Mapping(target = "endDate", source = "course.settings.endDate")
    @Mapping(target = "isPublic", source = "course.settings.isPublic")
    CourseResponseDto toDto(Course course);

    List<CourseResponseDto> toDtos(List<Course> courses);

    @Mapping(target = "settings.startDate", source = "startDate")
    @Mapping(target = "settings.endDate", source = "endDate")
    @Mapping(target = "settings.isPublic", source = "isPublic", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "coinsPaid", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Course toEntity(CourseRequestDto request);

    @Mapping(target = "settings.startDate", source = "startDate")
    @Mapping(target = "settings.endDate", source = "endDate")
    @Mapping(target = "settings.isPublic", source = "isPublic", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "coinsPaid", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void toEntity(CourseRequestDto request, @MappingTarget Course course);
}
