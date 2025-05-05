package org.example.learningsystem.mapper;

import org.example.learningsystem.domain.Student;
import org.example.learningsystem.dto.student.StudentRequestDto;
import org.example.learningsystem.dto.student.StudentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentResponseDto toDto(Student student);

    List<StudentResponseDto> toDtos(List<Student> students);

    @Mapping(target = "coins", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Student toEntity(StudentRequestDto studentRequestDto);

    @Mapping(target = "coins", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void toEntity(StudentRequestDto studentRequestDto, @MappingTarget Student student);
}
