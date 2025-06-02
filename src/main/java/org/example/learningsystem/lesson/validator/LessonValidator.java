package org.example.learningsystem.lesson.validator;

import org.example.learningsystem.core.util.validator.EntityValidator;
import org.example.learningsystem.lesson.model.Lesson;
import org.example.learningsystem.core.exception.model.validation.IllegalNullValueException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class LessonValidator implements EntityValidator<Lesson> {

    private static final String COURSE_PROPERTY = "class";

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
            throw new IllegalNullValueException(COURSE_PROPERTY);
        }
    }

}
