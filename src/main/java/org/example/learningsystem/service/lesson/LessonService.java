package org.example.learningsystem.service.lesson;

import org.example.learningsystem.domain.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    Lesson create(UUID courseId, Lesson lesson);

    Lesson getById(UUID id);

    List<Lesson> getAllByCourseId(UUID courseId);

    List<Lesson> getAll();

    Lesson update(Lesson lesson);

    void delete(UUID id);
}
