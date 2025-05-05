package org.example.learningsystem.service.student;

import org.example.learningsystem.domain.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    Student create(Student student);

    Student getById(UUID id);

    List<Student> getAllByCourseId(UUID courseId);

    List<Student> getAll();

    Student update(Student student);

    void delete(UUID id);
}
