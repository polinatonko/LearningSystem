package org.example.learningsystem.lesson.repository;

import org.example.learningsystem.lesson.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query("SELECT l, c FROM Lesson l LEFT JOIN Course c ON l.course.id = c.id")
    Optional<Lesson> findByIdWithCourse(UUID id);

    Page<Lesson> findAllByCourseId(UUID courseId, Pageable pageable);
}
