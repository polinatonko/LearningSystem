package org.example.learningsystem.lesson.common.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.util.validator.EntityValidator;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.lesson.common.model.Lesson;
import org.example.learningsystem.core.exception.model.EntityNotFoundException;
import org.example.learningsystem.lesson.common.repository.LessonRepository;
import org.example.learningsystem.course.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final EntityValidator<Lesson> lessonValidator;
    private final CourseService courseService;

    @Override
    public Lesson create(UUID courseId, Lesson lesson) {
        var course = courseService.getById(courseId);
        addToCourse(lesson, course);
        lessonValidator.validateForInsert(lesson);
        return lessonRepository.save(lesson);
    }

    @Override
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
    public Lesson update(UUID courseId, Lesson lesson) {
        var savedLesson = getByIdWithCourse(lesson.getId());

        lessonValidator.validateTypes(savedLesson, lesson);
        updateCourse(lesson, savedLesson.getCourse(), courseId);

        lessonValidator.validateForUpdate(lesson);
        return lessonRepository.save(lesson);
    }

    @Override
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
