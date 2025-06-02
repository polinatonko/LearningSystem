package org.example.learningsystem.course.service;

import org.example.learningsystem.common.builder.CourseBuilder;
import org.example.learningsystem.common.builder.StudentBuilder;
import org.example.learningsystem.course.exception.DuplicateEnrollmentException;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.course.model.CourseEnrollmentId;
import org.example.learningsystem.course.exception.EnrollmentDeniedException;
import org.example.learningsystem.course.exception.InsufficientFundsException;
import org.example.learningsystem.course.repository.CourseEnrollmentRepository;
import org.example.learningsystem.course.validator.CourseEnrollmentValidator;
import org.example.learningsystem.student.service.StudentServiceImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CourseEnrollmentServiceImplTest {

    @Mock
    public CourseEnrollmentRepository enrollmentRepository;
    @Mock
    public CourseServiceImpl courseService;
    @Mock
    public CourseEnrollmentValidator enrollmentValidator;
    @InjectMocks
    public CourseEnrollmentServiceImpl enrollmentService;
    @Mock
    public StudentServiceImpl studentService;

    @Test
    void enrollStudent_givenCourseAndStudent_shouldSuccessfullyEnrollStudent() {
        // given
        var course = new CourseBuilder()
                .id(UUID.randomUUID())
                .build();
        var student = new StudentBuilder()
                .id(UUID.randomUUID())
                .coins(course.getPrice())
                .build();

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
        var course = new CourseBuilder()
                .id(UUID.randomUUID())
                .build();
        var student = new StudentBuilder()
                .id(UUID.randomUUID())
                .coins(course.getPrice())
                .build();
        
        when(studentService.getByIdForUpdate(any()))
                .thenReturn(student);
        when(courseService.getByIdForUpdate(any()))
                .thenReturn(course);
        doThrow(EnrollmentDeniedException.class)
                .when(enrollmentValidator)
                .validateForInsert(any());

        // when, then
        assertThrows(EnrollmentDeniedException.class,
                () -> enrollmentService.enrollStudent(course.getId(), student.getId()));
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldThrowInsufficientFundsException() {
        // given
        var course = new CourseBuilder()
                .id(UUID.randomUUID())
                .build();
        var student = new StudentBuilder()
                .id(UUID.randomUUID())
                .build();

        when(studentService.getByIdForUpdate(any()))
                .thenReturn(student);
        when(courseService.getByIdForUpdate(any()))
                .thenReturn(course);
        doThrow(InsufficientFundsException.class)
                .when(enrollmentValidator)
                .validateForInsert(any());

        // when, then
        assertThrows(InsufficientFundsException.class,
                () -> enrollmentService.enrollStudent(course.getId(), student.getId()));
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldThrowDuplicateEnrollmentException() {
        // given
        var course = new CourseBuilder()
                .id(UUID.randomUUID())
                .build();
        var courseId = course.getId();

        var student = new StudentBuilder()
                .id(UUID.randomUUID())
                .build();
        var studentId = student.getId();

        when(courseService.getByIdForUpdate(courseId))
                .thenReturn(course);
        when(studentService.getByIdForUpdate(studentId))
                .thenReturn(student);
        doThrow(DuplicateEnrollmentException.class)
                .when(enrollmentValidator)
                .validateForInsert(any());

        // when, then
        assertThrows(DuplicateEnrollmentException.class,
                () -> enrollmentService.enrollStudent(courseId, studentId));
    }

    @Test
    void unenrollStudent_givenCourseIdAndStudentId_shouldSuccessfullyUnenrollStudent() {
        // given
        var course = new CourseBuilder()
                .id(UUID.randomUUID())
                .build();
        var courseId = course.getId();

        var student = new StudentBuilder()
                .id(UUID.randomUUID())
                .build();
        var studentId = student.getId();

        // when
        enrollmentService.unenrollStudent(courseId, studentId);

        // then
        verify(enrollmentRepository, times(1))
                .deleteById(new CourseEnrollmentId(courseId, studentId));
    }

}
