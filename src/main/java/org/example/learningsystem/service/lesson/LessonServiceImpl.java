package org.example.learningsystem.service.lesson;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Course;
import org.example.learningsystem.domain.Lesson;
import org.example.learningsystem.exception.logic.EntityNotFoundException;
import org.example.learningsystem.repository.LessonRepository;
import org.example.learningsystem.service.course.CourseService;
import org.example.learningsystem.validator.LessonValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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
        lessonValidator.validateForUpdate(lesson);
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

    private void addLessonToCourse(Course course, Lesson lesson) {
        lesson.setCourse(course);
        var courseLessons = course.getLessons();
        courseLessons.add(lesson);
    }
}
