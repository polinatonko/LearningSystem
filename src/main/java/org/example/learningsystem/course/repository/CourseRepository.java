package org.example.learningsystem.course.repository;

import org.example.learningsystem.course.model.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    @EntityGraph(attributePaths = {"lessons"})
    Optional<Course> findById(UUID id);

    @EntityGraph(attributePaths = {"enrollments", "enrollments.student"})
    List<Course> findAllBySettingsStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}