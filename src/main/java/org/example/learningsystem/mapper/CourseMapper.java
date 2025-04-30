package org.example.learningsystem.mapper;

import org.example.learningsystem.domain.Course;
import org.example.learningsystem.dto.course.CourseRequest;
import org.example.learningsystem.dto.course.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "startDate", source = "course.settings.startDate")
    @Mapping(target = "endDate", source = "course.settings.endDate")
    @Mapping(target = "isPublic", source = "course.settings.isPublic")
    CourseResponse courseToResponse(Course course);
    @Mapping(target = "settings.startDate", source = "startDate")
    @Mapping(target = "settings.endDate", source = "endDate")
    @Mapping(target = "settings.isPublic", source = "isPublic", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "coinsPaid", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Course requestToCourse(CourseRequest request);
}
