package org.example.learningsystem.lesson.repository;

import org.example.learningsystem.lesson.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    List<Lesson> findAllByCourseId(UUID courseId);
}
