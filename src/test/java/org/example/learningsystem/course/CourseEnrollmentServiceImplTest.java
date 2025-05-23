package org.example.learningsystem.course;

import org.example.learningsystem.builder.CourseBuilder;
import org.example.learningsystem.builder.StudentBuilder;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.course.model.CourseEnrollmentId;
import org.example.learningsystem.course.exception.EnrollmentDeniedException;
import org.example.learningsystem.course.exception.InsufficientFundsException;
import org.example.learningsystem.course.repository.CourseEnrollmentRepository;
import org.example.learningsystem.course.service.CourseServiceImpl;
import org.example.learningsystem.course.service.CourseEnrollmentServiceImpl;
import org.example.learningsystem.course.validator.CourseEnrollmentValidator;
import org.example.learningsystem.student.model.Student;
import org.example.learningsystem.student.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseEnrollmentServiceImplTest {

    @Mock
    public CourseEnrollmentRepository enrollmentRepository;
    @Mock
    public CourseServiceImpl courseService;
    @InjectMocks
    public CourseEnrollmentValidator enrollmentValidator;
    public CourseEnrollmentServiceImpl enrollmentService;
    @Mock
    public StudentServiceImpl studentService;
    private Course course;
    private Student student;

    @BeforeEach
    void setup() {
        enrollmentService = new CourseEnrollmentServiceImpl(courseService, enrollmentRepository, enrollmentValidator, studentService);

        course = new CourseBuilder()
                .id(UUID.randomUUID())
                .isPublic(true)
                .build();

        student = new StudentBuilder()
                .id(UUID.randomUUID())
                .build();
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldSuccessfullyEnrollStudent() {
        // given
        student.setCoins(course.getPrice());
        when(studentService.getByIdForUpdate(any()))
                .thenReturn(student);
        when(courseService.getByIdForUpdate(any()))
                .thenReturn(course);

        // when
        enrollmentService.enrollStudent(course.getId(), student.getId());

        // then
        verify(enrollmentRepository, times(1))
                .save(Mockito.any(CourseEnrollment.class));
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldThrowEnrollmentDeniedException() {
        // given
        course.getSettings().setIsPublic(false);
        when(studentService.getByIdForUpdate(any()))
                .thenReturn(student);
        when(courseService.getByIdForUpdate(any()))
                .thenReturn(course);

        // when, then
        assertThrows(EnrollmentDeniedException.class,
                () -> enrollmentService.enrollStudent(course.getId(), student.getId()));
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldThrowInsufficientFundsException() {
        // given
        student.setCoins(course.getPrice().subtract(BigDecimal.ONE));
        when(studentService.getByIdForUpdate(any()))
                .thenReturn(student);
        when(courseService.getByIdForUpdate(any()))
                .thenReturn(course);

        // when, then
        assertThrows(InsufficientFundsException.class,
                () -> enrollmentService.enrollStudent(course.getId(), student.getId()));
    }

    @Test
    void unenrollStudent_givenCourseIdAndStudentId_shouldSuccessfullyUnenrollStudent() {
        // given
        var courseId = course.getId();
        var studentId = student.getId();

        // when
        enrollmentService.unenrollStudent(courseId, studentId);

        // then
        verify(enrollmentRepository, times(1))
                .deleteById(new CourseEnrollmentId(courseId, studentId));
    }
}
