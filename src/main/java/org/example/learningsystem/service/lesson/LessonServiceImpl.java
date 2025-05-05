package org.example.learningsystem.service.lesson;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Lesson;
import org.example.learningsystem.exception.logic.EntityNotFoundException;
import org.example.learningsystem.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public Lesson create(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson getById(UUID id) {
        return findById(id);
    }

    @Override
    public List<Lesson> getAllByCourseId(UUID courseId) {
        return lessonRepository.findAllByCourseId(courseId);
    }

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson update(Lesson lesson) {
        findById(lesson.getId());
        return lessonRepository.save(lesson);
    }

    @Override
    public void delete(UUID id) {
        lessonRepository.deleteById(id);
    }

    private Lesson findById(UUID id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Lesson.class.getName(), id));
    }
}
