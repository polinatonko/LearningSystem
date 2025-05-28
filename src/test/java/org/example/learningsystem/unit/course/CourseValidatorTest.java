package org.example.learningsystem.unit.course;

import org.example.learningsystem.course.exception.InvalidCourseDurationException;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.validator.CourseValidator;
import org.example.learningsystem.exception.validation.IllegalNullValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.learningsystem.util.CourseTestUtils.createCourse;
import static org.example.learningsystem.util.CourseTestUtils.END_DATE;
import static org.example.learningsystem.util.CourseTestUtils.START_DATE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit-test")
@ExtendWith(MockitoExtension.class)
class CourseValidatorTest {

    private final CourseValidator courseValidator = new CourseValidator();
    private Course course;

    @BeforeEach
    void setup() {
        course = createCourse();
    }

    @Test
    void validateForInsert_givenCourse_shouldSuccessfullyExecute() {
        // when, then
        assertDoesNotThrow(() -> courseValidator.validateForInsert(course));
    }

    @Test
    void validateForInsert_givenCourse_shouldThrowInvalidCourseDurationException() {
        // given
        setInvalidDuration(course);

        // when, then
        assertThrows(InvalidCourseDurationException.class,
                () -> courseValidator.validateForInsert(course));
    }

    @Test
    void validateForUpdate_givenCourse_shouldSuccessfullyExecute() {
        // when, then
        assertDoesNotThrow(() -> courseValidator.validateForUpdate(course));
    }

    @Test
    void validateForUpdate_givenCourse_shouldThrowInvalidCourseDurationException() {
        // given
        setInvalidDuration(course);

        // when, then
        assertThrows(InvalidCourseDurationException.class,
                () -> courseValidator.validateForUpdate(course));
    }

    @Test
    void validateForUpdate_givenCourse_shouldThrowIllegalNullValueException() {
        // given
        course.setCoinsPaid(null);

        // when, then
        assertThrows(IllegalNullValueException.class,
                () -> courseValidator.validateForUpdate(course));
    }

    private void setInvalidDuration(Course course) {
        var settings = course.getSettings();
        settings.setStartDate(END_DATE);
        settings.setEndDate(START_DATE);
    }

}
