package org.example.learningsystem.course.service;

import org.example.learningsystem.course.model.Course;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    Course create(Course course);

    Course getById(UUID id);

    Course getByIdForUpdate(UUID id);

    List<Course> getAll();

    List<Course> getUpcoming(int days);

    Course update(Course course);

    void deleteById(UUID id);
}
