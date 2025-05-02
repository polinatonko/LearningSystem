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
    private final LessonRepository repository;

    @Override
    public Lesson create(Lesson lesson) {
        return repository.save(lesson);
    }

    @Override
    public Lesson getById(UUID id) {
        return findById(id);
    }

    @Override
    public List<Lesson> getAllByCourseId(UUID courseId) {
        return repository.findAllByCourseId(courseId);
    }

    @Override
    public List<Lesson> getAll() {
        return repository.findAll();
    }

    @Override
    public Lesson update(Lesson lesson) {
        findById(lesson.getId());
        return repository.save(lesson);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private Lesson findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Lesson.class.getName(), id));
    }
}
