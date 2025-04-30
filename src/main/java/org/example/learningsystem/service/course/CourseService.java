package org.example.learningsystem.service.course;

import org.example.learningsystem.domain.Course;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    Course create(Course course);
    void enrollStudent(UUID studentId, UUID courseId);
    Course getById(UUID id);
    List<Course> getAll();
    Course update(Course course);
    void delete(UUID id);
    void unenrollStudent(UUID courseId, UUID studentId);
}
