package org.example.learningsystem.course.validator;

import org.example.learningsystem.util.CourseBuilder;
import org.example.learningsystem.course.exception.InvalidCourseDurationException;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.core.exception.validation.IllegalNullValueException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CourseValidatorTest {

    private final CourseValidator courseValidator = new CourseValidator();

    @Test
    void validateForInsert_givenCourse_shouldSuccessfullyExecute() {
        // given
        var course = new CourseBuilder().build();

        // when, then
        assertDoesNotThrow(() -> courseValidator.validateForInsert(course));
    }

    @Test
    void validateForInsert_givenCourse_shouldThrowInvalidCourseDurationException() {
        // given
        var course = new CourseBuilder().build();
        setInvalidDuration(course);

        // when, then
        assertThrows(InvalidCourseDurationException.class,
                () -> courseValidator.validateForInsert(course));
    }

    @Test
    void validateForUpdate_givenCourse_shouldSuccessfullyExecute() {
        // given
        var course = new CourseBuilder().build();

        // when, then
        assertDoesNotThrow(() -> courseValidator.validateForUpdate(course));
    }

    @Test
    void validateForUpdate_givenCourse_shouldThrowInvalidCourseDurationException() {
        // given
        var course = new CourseBuilder().build();
        setInvalidDuration(course);

        // when, then
        assertThrows(InvalidCourseDurationException.class,
                () -> courseValidator.validateForUpdate(course));
    }

    @Test
    void validateForUpdate_givenCourse_shouldThrowIllegalNullValueException() {
        // given
        var course = new CourseBuilder()
                .coinsPaid(null)
                .build();

        // when, then
        assertThrows(IllegalNullValueException.class,
                () -> courseValidator.validateForUpdate(course));
    }

    private void setInvalidDuration(Course course) {
        var settings = course.getSettings();
        settings.setStartDate(LocalDateTime.of(2025, 2, 1, 10, 0, 0));
        settings.setEndDate(LocalDateTime.of(2025, 1, 1, 10, 0, 0));
    }

}
