package org.example.learningsystem.course;

import org.example.learningsystem.builder.CourseBuilder;
import org.example.learningsystem.builder.StudentBuilder;
import org.example.learningsystem.LearningSystemApplication;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {LearningSystemApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CourseEnrollmentTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    private Course course;
    private static final String ENROLLMENT_URL = "/courses/{id}/students/{studentId}";

    @BeforeEach
    void setup() {
        course = new CourseBuilder()
                .isPublic(true)
                .build();
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldSuccessfullyEnrollStudentAndReturn200() throws Exception {
        // given
        var savedCourse = courseService.create(course);
        var courseId = savedCourse.getId();

        var student = new StudentBuilder()
                .coins(savedCourse.getPrice())
                .build();
        var savedStudent = studentService.create(student);
        var studentId = savedStudent.getId();

        // when
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isOk());

        // then
        var courseStudents = studentService.getAllByCourseId(courseId);
        assertEquals(1, courseStudents.size());
        var updatedStudent = courseStudents.getFirst();
        assertEquals(studentId, updatedStudent.getId());

        assertNotEquals(student.getCoins(), updatedStudent.getCoins());
        var updatedCourse = courseService.getById(courseId);
        assertNotEquals(course.getCoinsPaid(), updatedCourse.getCoinsPaid());
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldThrowEnrollmentDeniedExceptionAndReturn500() throws Exception {
        // given
        course.getSettings().setIsPublic(false);
        var savedCourse = courseService.create(course);

        var student = new StudentBuilder().build();
        var savedStudent = studentService.create(student);

        // when, then
        mockMvc.perform(post(ENROLLMENT_URL, savedCourse.getId(), savedStudent.getId()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldThrowInsufficientFundsExceptionAndReturn500() throws Exception {
        // given
        var savedCourse = courseService.create(course);

        var student = new StudentBuilder()
                .coins(savedCourse.getPrice().subtract(BigDecimal.ONE))
                .build();
        var savedStudent = studentService.create(student);

        // when, then
        mockMvc.perform(post(ENROLLMENT_URL, savedCourse.getId(), savedStudent.getId()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void unenrollStudent_givenCourseAndStudent_shouldSuccessfullyUnenrollStudentAndReturn204() throws Exception {
        // given
        var savedCourse = courseService.create(course);
        var courseId = savedCourse.getId();

        var student = new StudentBuilder()
                .coins(savedCourse.getPrice())
                .build();
        var savedStudent = studentService.create(student);
        var studentId = savedStudent.getId();

        // when
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isOk());
        mockMvc.perform(delete(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isNoContent());

        // then
        var courseStudents = studentService.getAllByCourseId(courseId);
        assertEquals(0, courseStudents.size());
    }
}
