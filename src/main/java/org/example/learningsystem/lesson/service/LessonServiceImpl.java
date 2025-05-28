package org.example.learningsystem.lesson.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.util.validator.EntityValidator;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.lesson.model.Lesson;
import org.example.learningsystem.exception.logic.EntityNotFoundException;
import org.example.learningsystem.lesson.repository.LessonRepository;
import org.example.learningsystem.course.service.CourseService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "lesson")
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final EntityValidator<Lesson> lessonValidator;
    private final CourseService courseService;

    @Override
    @Transactional
    public Lesson create(UUID courseId, Lesson lesson) {
        var course = courseService.getById(courseId);
        addToCourse(lesson, course);
        lessonValidator.validateForInsert(lesson);
        return lessonRepository.save(lesson);
    }

    @Override
    @Cacheable
    public Lesson getById(UUID id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Lesson.class.getName(), id));
    }

    @Override
    public Page<Lesson> getAllByCourseId(UUID courseId, Pageable pageable) {
        return lessonRepository.findAllByCourseId(courseId, pageable);
    }

    @Override
    public Page<Lesson> getAll(Pageable pageable) {
        return lessonRepository.findAll(pageable);
    }

    @Override
    @CachePut(key = "#lesson.id")
    public Lesson update(UUID courseId, Lesson lesson) {
        var savedLesson = getByIdWithCourse(lesson.getId());

        lessonValidator.validateTypes(savedLesson, lesson);
        updateCourse(lesson, savedLesson.getCourse(), courseId);

        lessonValidator.validateForUpdate(lesson);
        return lessonRepository.save(lesson);
    }

    @Override
    @CacheEvict
    public void deleteById(UUID id) {
        lessonRepository.deleteById(id);
    }

    private Lesson getByIdWithCourse(UUID id) {
        return lessonRepository.findByIdWithCourse(id)
                .orElseThrow(() -> new EntityNotFoundException(Lesson.class.getName(), id));
    }

    private void addToCourse(Lesson lesson, Course course) {
        lesson.setCourse(course);
        var courseLessons = course.getLessons();
        courseLessons.add(lesson);
    }

    private void updateCourse(Lesson lesson, Course course, UUID newCourseId) {
        var courseId = course.getId();
        if (nonNull(newCourseId) && !courseId.equals(newCourseId)) {
            var newCourse = courseService.getById(newCourseId);
            addToCourse(lesson, newCourse);
        } else {
            lesson.setCourse(course);
        }
    }

}
