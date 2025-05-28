package org.example.learningsystem.unit.course;

import org.example.learningsystem.course.exception.DuplicateEnrollmentException;
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

import static org.example.learningsystem.util.CourseTestUtils.ENOUGH_COINS;
import static org.example.learningsystem.util.CourseTestUtils.createSavedCourse;
import static org.example.learningsystem.util.StudentTestUtils.createSavedStudent;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private Course course;
    private Student student;

    @BeforeEach
    void setup() {
        course = createSavedCourse();
        student = createSavedStudent();
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldSuccessfullyEnrollStudent() {
        // given
        student.setCoins(ENOUGH_COINS);
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
        var courseId = course.getId();
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
        var courseId = course.getId();
        var studentId = student.getId();

        // when
        enrollmentService.unenrollStudent(courseId, studentId);

        // then
        verify(enrollmentRepository, times(1))
                .deleteById(new CourseEnrollmentId(courseId, studentId));
    }

}
