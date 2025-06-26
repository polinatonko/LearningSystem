package org.example.learningsystem.student.service;

import org.example.learningsystem.student.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StudentService {

    Student create(Student student);

    Student getById(UUID id);

    Student getByIdForUpdate(UUID id);

    Page<Student> getAllByCourseId(UUID courseId, Pageable pageable);

    Page<Student> getAll(Pageable pageable);

    Student update(Student student);

    void deleteById(UUID id);
}
