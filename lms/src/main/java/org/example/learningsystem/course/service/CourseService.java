package org.example.learningsystem.course.service;

import org.example.learningsystem.course.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    Course create(Course course);

    Course getById(UUID id);

    Course getByIdForUpdate(UUID id);

    Page<Course> getAll(Pageable pageable);

    List<Course> getUpcoming(int days);

    Course update(Course course);

    void deleteById(UUID id);
}
