package org.example.learningsystem.lesson.service;

import org.example.learningsystem.lesson.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LessonService {

    Lesson create(UUID courseId, Lesson lesson);

    Lesson getById(UUID id);

    Page<Lesson> getAllByCourseId(UUID courseId, Pageable pageable);

    Page<Lesson> getAll(Pageable pageable);

    Lesson update(Lesson lesson);

    void deleteById(UUID id);
}
