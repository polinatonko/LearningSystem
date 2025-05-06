package org.example.learningsystem.validator;

import org.example.learningsystem.domain.Lesson;
import org.example.learningsystem.exception.validation.IllegalNullValueException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class LessonValidator implements EntityValidator<Lesson> {
    @Override
    public void validateForInsert(Lesson lesson) {
        validateCourseId(lesson);
    }

    @Override
    public void validateForUpdate(Lesson lesson) {
        validateCourseId(lesson);
    }

    private void validateCourseId(Lesson lesson) {
        var course = lesson.getCourse();
        if (isNull(course) || isNull(course.getId())) {
            throw new IllegalNullValueException("course");
        }
    }
}
