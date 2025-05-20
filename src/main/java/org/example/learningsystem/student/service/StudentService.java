package org.example.learningsystem.student.service;

import org.example.learningsystem.student.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    Student create(Student student);

    Student getById(UUID id);

    Student getByIdForUpdate(UUID id);

    List<Student> getAllByCourseId(UUID courseId);

    List<Student> getAll();

    Student update(Student student);

    void deleteById(UUID id);
}
