package org.example.learningsystem.repository;

import org.example.learningsystem.domain.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    @EntityGraph(attributePaths = {"enrollments", "enrollments.student"})
    List<Course> findAllBySettingsStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}