package org.example.learningsystem.student.repository;

import org.example.learningsystem.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    List<Student> findAllByEnrollmentsCourseId(UUID courseId);
}
