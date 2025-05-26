package org.example.learningsystem.lesson.repository;

import org.example.learningsystem.lesson.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    Page<Lesson> findAllByCourseId(UUID courseId, Pageable pageable);
}
