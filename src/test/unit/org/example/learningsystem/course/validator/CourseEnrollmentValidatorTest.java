package org.example.learningsystem.course.validator;

import org.example.learningsystem.course.common.builder.CourseBuilder;
import org.example.learningsystem.student.common.builder.StudentBuilder;
import org.example.learningsystem.course.exception.DuplicateEnrollmentException;
import org.example.learningsystem.course.exception.EnrollmentDeniedException;
import org.example.learningsystem.course.exception.InsufficientFundsException;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.course.repository.CourseEnrollmentRepository;
import org.example.learningsystem.student.model.Student;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CourseEnrollmentValidatorTest {

    @Mock
    public CourseEnrollmentRepository courseEnrollmentRepository;
    @InjectMocks
    public CourseEnrollmentValidator courseEnrollmentValidator;

    @Test
    void validateForInsert_givenCourseEnrollment_shouldDoesNotThrowException() {
        // given
        var course = new CourseBuilder().build();
        var student = buildStudentWithCoins(course.getPrice());
        var courseEnrollment = new CourseEnrollment(course, student);

        // when, then
        assertDoesNotThrow(() -> courseEnrollmentValidator.validateForInsert(courseEnrollment));
    }

    @Test
    void validateForInsert_givenCourseEnrollmentWithPrivateCourse_shouldThrowEnrollmentDeniedException() {
        // given
        var course = new CourseBuilder()
                .isPublic(false)
                .build();
        var student = buildStudentWithCoins(course.getPrice());
        var courseEnrollment = new CourseEnrollment(course, student);

        // when, then
        assertThrows(EnrollmentDeniedException.class,
                () -> courseEnrollmentValidator.validateForInsert(courseEnrollment));
    }

    @Test
    void validateForInsert_givenCourseEnrollmentWithNullPrice_shouldThrowEnrollmentDeniedException() {
        // given
        var course = new CourseBuilder()
                .price(null)
                .build();
        var student = buildStudentWithCoins(course.getPrice());
        var courseEnrollment = new CourseEnrollment(course, student);

        // when, then
        assertThrows(EnrollmentDeniedException.class,
                () -> courseEnrollmentValidator.validateForInsert(courseEnrollment));
    }

    @Test
    void validateForInsert_givenCourseEnrollment_shouldThrowDuplicateEnrollmentException() {
        // given
        var course = new CourseBuilder().build();
        var student = buildStudentWithCoins(course.getPrice());
        var courseEnrollment = new CourseEnrollment(course, student);

        when(courseEnrollmentRepository.existsByCourseIdAndStudentId(any(), any()))
                .thenReturn(true);

        // when, then
        assertThrows(DuplicateEnrollmentException.class,
                () -> courseEnrollmentValidator.validateForInsert(courseEnrollment));
    }

    @Test
    void validateForInsert_givenCourseEnrollment_shouldThrowInsufficientFundsException() {
        // given
        var course = new CourseBuilder().build();
        var price = course.getPrice();
        var student = buildStudentWithCoins(price.subtract(BigDecimal.ONE));
        var courseEnrollment = new CourseEnrollment(course, student);

        // when, then
        assertThrows(InsufficientFundsException.class,
                () -> courseEnrollmentValidator.validateForInsert(courseEnrollment));
    }

    private Student buildStudentWithCoins(BigDecimal coins) {
        return new StudentBuilder()
                .coins(coins)
                .build();
    }
}
