package org.example.learningsystem.lesson.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.lesson.model.Lesson;
import org.example.learningsystem.exception.logic.EntityNotFoundException;
import org.example.learningsystem.lesson.repository.LessonRepository;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.lesson.validator.LessonValidator;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "lesson")
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonValidator lessonValidator;
    private final CourseService courseService;

    @Override
    public Lesson create(UUID courseId, Lesson lesson) {
        var course = courseService.getById(courseId);
        addLessonToCourse(course, lesson);
        lessonValidator.validateForInsert(lesson);
        return lessonRepository.save(lesson);
    }

    @Override
    @Cacheable
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
    @CachePut(key = "#lesson.id")
    public Lesson update(Lesson lesson) {
        findById(lesson.getId());
        lessonValidator.validateForUpdate(lesson);
        return lessonRepository.save(lesson);
    }

    @Override
    @CacheEvict
    public void deleteById(UUID id) {
        lessonRepository.deleteById(id);
    }

    private Lesson findById(UUID id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Lesson.class.getName(), id));
    }

    private void addLessonToCourse(Course course, Lesson lesson) {
        lesson.setCourse(course);
        var courseLessons = course.getLessons();
        courseLessons.add(lesson);
    }
}
