package org.example.learningsystem.service;

import org.example.learningsystem.builder.CourseBuilder;
import org.example.learningsystem.builder.StudentBuilder;
import org.example.learningsystem.domain.*;
import org.example.learningsystem.exception.enrollment.EnrollmentDeniedException;
import org.example.learningsystem.exception.enrollment.InsufficientFundsException;
import org.example.learningsystem.repository.EnrollmentRepository;
import org.example.learningsystem.service.course.CourseServiceImpl;
import org.example.learningsystem.service.course.CourseEnrollmentServiceImpl;
import org.example.learningsystem.service.student.StudentServiceImpl;
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
    public EnrollmentRepository enrollmentRepository;
    @Mock
    public CourseServiceImpl courseService;
    @InjectMocks
    public CourseEnrollmentServiceImpl enrollmentService;
    @Mock
    public StudentServiceImpl studentService;
    private Course course;
    private Student student;

    @BeforeEach
    void setup() {
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
        when(studentService.getById(any()))
                .thenReturn(student);
        when(courseService.getById(any()))
                .thenReturn(course);

        // when
        enrollmentService.enrollStudent(course.getId(), student.getId());

        // then
        verify(enrollmentRepository, times(1))
                .save(Mockito.any(Enrollment.class));
        verify(courseService, times(1))
                .update(any(Course.class));
        verify(studentService, times(1))
                .update(any(Student.class));
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldThrowEnrollmentDeniedException() {
        // given
        course.getSettings().setIsPublic(false);
        when(studentService.getById(any()))
                .thenReturn(student);
        when(courseService.getById(any()))
                .thenReturn(course);

        // when, then
        assertThrows(EnrollmentDeniedException.class,
                () -> enrollmentService.enrollStudent(course.getId(), student.getId()));
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldThrowInsufficientFundsException() {
        // given
        student.setCoins(course.getPrice().subtract(BigDecimal.ONE));
        when(studentService.getById(any()))
                .thenReturn(student);
        when(courseService.getById(any()))
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
                .deleteById(new EnrollmentId(courseId, studentId));
    }
}
