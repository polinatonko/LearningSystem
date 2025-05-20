package org.example.learningsystem.course.repository;

import org.example.learningsystem.course.model.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    @EntityGraph(attributePaths = {"lessons"})
    Optional<Course> findById(UUID id);

    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT c, l FROM Course c LEFT JOIN Lesson l ON l.course.id = c.id")
    Optional<Course> findByIdForUpdate(UUID id);

    @EntityGraph(attributePaths = {"enrollments", "enrollments.student"})
    List<Course> findAllBySettingsStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}