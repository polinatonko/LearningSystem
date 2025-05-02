package org.example.learningsystem.service;

import org.example.learningsystem.domain.*;
import org.example.learningsystem.exception.logic.CourseEnrollmentException;
import org.example.learningsystem.repository.CourseRepository;
import org.example.learningsystem.repository.EnrollmentRepository;
import org.example.learningsystem.repository.StudentRepository;
import org.example.learningsystem.service.course.CourseServiceImpl;
import org.example.learningsystem.service.course.EnrollmentServiceImpl;
import org.example.learningsystem.service.student.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceImplTests {
    @Mock
    public CourseRepository courseRepository;
    @Mock
    public EnrollmentRepository enrollmentRepository;
    @Mock
    public StudentRepository studentRepository;
    public CourseServiceImpl courseService;
    public EnrollmentServiceImpl enrollmentService;
    public StudentServiceImpl studentService;
    private Course course;
    private Student student;

    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl(courseRepository);
        studentService = new StudentServiceImpl(studentRepository);
        enrollmentService = new EnrollmentServiceImpl(enrollmentRepository, courseService, studentService);

        course = new Course("Test course", "Course description", BigDecimal.ZERO);
        course.setId(UUID.randomUUID());
        course.setSettings(CourseSettings.builder().isPublic(true).build());
        course.setEnrollments(Set.of());

        student = new Student("Name", "Surname", "name@gmail.com",
                LocalDate.of(1900, 1, 1), BigDecimal.ZERO);
        student.setId(UUID.randomUUID());
        student.setEnrollments(Set.of());
    }

    @Test
    void testSuccessEnrollment() {
        course.setPrice(BigDecimal.ZERO);
        course.getSettings().setIsPublic(true);
        student.setCoins(BigDecimal.ZERO);
        when(studentRepository.findById(any())).thenReturn(Optional.of(student));
        when(courseRepository.findById(any())).thenReturn(Optional.of(course));

        enrollmentService.enrollStudent(course.getId(), student.getId());

        Mockito.verify(enrollmentRepository, Mockito.times(1))
                .save(Mockito.any(Enrollment.class));
        Mockito.verify(courseRepository, Mockito.times(1))
                .save(Mockito.any(Course.class));
        Mockito.verify(studentRepository, Mockito.times(1))
                .save(Mockito.any(Student.class));
    }

    @Test
    void testEnrollmentDenied() {
        course.getSettings().setIsPublic(false);
        when(courseRepository.findById(any())).thenReturn(Optional.of(course));

        assertThrows(CourseEnrollmentException.EnrollmentDeniedException.class,
                () -> enrollmentService.enrollStudent(course.getId(), student.getId()));
    }

    @Test
    void testInsufficientFunds() {
        course.setPrice(BigDecimal.ONE);
        course.getSettings().setIsPublic(true);
        student.setCoins(BigDecimal.ZERO);
        when(studentRepository.findById(any())).thenReturn(Optional.of(student));
        when(courseRepository.findById(any())).thenReturn(Optional.of(course));

        assertThrows(CourseEnrollmentException.InsufficientFundsException.class,
                () -> enrollmentService.enrollStudent(course.getId(), student.getId()));
    }

    @Test
    void testSuccessUnenrollment() {
        enrollmentService.unenrollStudent(course.getId(), student.getId());

        Mockito.verify(enrollmentRepository, Mockito.times(1))
                .deleteById(new EnrollmentId(course.getId(), student.getId()));
    }
}
