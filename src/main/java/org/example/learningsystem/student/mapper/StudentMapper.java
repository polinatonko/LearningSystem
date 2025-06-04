package org.example.learningsystem.student.mapper;

import org.example.learningsystem.student.model.Student;
import org.example.learningsystem.student.dto.StudentRequestDto;
import org.example.learningsystem.student.dto.StudentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = SPRING)
public interface StudentMapper {

    StudentResponseDto toDto(Student student);

    @Mapping(target = "coins", nullValueCheckStrategy = ALWAYS)
    Student toEntity(StudentRequestDto studentRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coins", nullValueCheckStrategy = ALWAYS)
    void toEntity(StudentRequestDto studentRequestDto, @MappingTarget Student student);
}
