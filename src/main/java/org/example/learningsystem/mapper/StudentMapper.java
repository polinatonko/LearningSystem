package org.example.learningsystem.mapper;

import org.example.learningsystem.domain.Student;
import org.example.learningsystem.dto.student.StudentRequest;
import org.example.learningsystem.dto.student.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentResponse studentToResponse(Student student);
    @Mapping(target = "coins", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Student requestToStudent(StudentRequest studentRequest);
}
