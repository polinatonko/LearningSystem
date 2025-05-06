package org.example.learningsystem.course.validator;

import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseSettings;
import org.example.learningsystem.course.exception.InvalidCourseDurationException;
import org.example.learningsystem.exception.validation.IllegalNullValueException;
import org.example.learningsystem.core.util.validator.EntityValidator;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class CourseValidator implements EntityValidator<Course> {

    public void validateForInsert(Course course) {
        var settings = course.getSettings();
        validateSettings(settings);
    }

    @Override
    public void validateForUpdate(Course course) {
        var settings = course.getSettings();
        validateSettings(settings);

        var coinsPaid = course.getCoinsPaid();
        if (isNull(coinsPaid)) {
            throw new IllegalNullValueException("coinsPaid");
        }
    }

    private void validateSettings(CourseSettings settings) {
        if (isNull(settings)) {
            return;
        }

        var startDate = settings.getStartDate();
        var endDate = settings.getEndDate();
        if (nonNull(settings.getStartDate()) && nonNull(settings.getEndDate()) && startDate.isAfter(endDate)) {
            throw new InvalidCourseDurationException(startDate, endDate);
        }
    }
}
