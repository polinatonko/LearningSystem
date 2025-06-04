package org.example.learningsystem.lesson.common.repository;

import org.example.learningsystem.lesson.common.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query("SELECT l FROM Lesson l LEFT JOIN FETCH l.course WHERE l.id = :id")
    Optional<Lesson> findByIdWithCourse(UUID id);

    Page<Lesson> findAllByCourseId(UUID courseId, Pageable pageable);
}
