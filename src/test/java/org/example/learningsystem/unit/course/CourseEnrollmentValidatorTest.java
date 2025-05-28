package org.example.learningsystem.unit.course;

import org.example.learningsystem.course.exception.DuplicateEnrollmentException;
import org.example.learningsystem.course.exception.EnrollmentDeniedException;
import org.example.learningsystem.course.exception.InsufficientFundsException;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.course.repository.CourseEnrollmentRepository;
import org.example.learningsystem.course.validator.CourseEnrollmentValidator;
import org.example.learningsystem.student.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.learningsystem.util.CourseTestUtils.createSavedCourse;
import static org.example.learningsystem.util.CourseTestUtils.ENOUGH_COINS;
import static org.example.learningsystem.util.CourseTestUtils.NOT_ENOUGH_COINS;
import static org.example.learningsystem.util.StudentTestUtils.createSavedStudent;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag("unit-test")
@ExtendWith(MockitoExtension.class)
class CourseEnrollmentValidatorTest {

    @Mock
    public CourseEnrollmentRepository courseEnrollmentRepository;
    @InjectMocks
    public CourseEnrollmentValidator courseEnrollmentValidator;
    private Course course;
    private CourseEnrollment courseEnrollment;
    private Student student;

    @BeforeEach
    void setup() {
        course = createSavedCourse();
        student = createSavedStudent();
        student.setCoins(ENOUGH_COINS);
        courseEnrollment = new CourseEnrollment(course, student);
    }

    @Test
    void validateForInsert_givenCourseEnrollment_shouldDoesNotThrowException() {
        // when, then
        assertDoesNotThrow(() -> courseEnrollmentValidator.validateForInsert(courseEnrollment));
    }

    @Test
    void validateForInsert_givenCourseEnrollmentWithPrivateCourse_shouldThrowEnrollmentDeniedException() {
        // given
        var settings = course.getSettings();
        settings.setIsPublic(false);

        // when, then
        assertThrows(EnrollmentDeniedException.class,
                () -> courseEnrollmentValidator.validateForInsert(courseEnrollment));
    }

    @Test
    void validateForInsert_givenCourseEnrollmentWithNullPrice_shouldThrowEnrollmentDeniedException() {
        // given
        course.setPrice(null);

        // when, then
        assertThrows(EnrollmentDeniedException.class,
                () -> courseEnrollmentValidator.validateForInsert(courseEnrollment));
    }

    @Test
    void validateForInsert_givenCourseEnrollment_shouldThrowDuplicateEnrollmentException() {
        // given
        when(courseEnrollmentRepository.existsByCourseIdAndStudentId(any(), any()))
                .thenReturn(true);

        // when, then
        assertThrows(DuplicateEnrollmentException.class,
                () -> courseEnrollmentValidator.validateForInsert(courseEnrollment));
    }

    @Test
    void validateForInsert_givenCourseEnrollment_shouldThrowInsufficientFundsException() {
        // given
        student.setCoins(NOT_ENOUGH_COINS);

        // when, then
        assertThrows(InsufficientFundsException.class,
                () -> courseEnrollmentValidator.validateForInsert(courseEnrollment));
    }

}
