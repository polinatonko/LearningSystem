package org.example.learningsystem.course;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.builder.CourseBuilder;
import org.example.learningsystem.builder.StudentBuilder;
import org.example.learningsystem.config.PostgreSQLConfiguration;
import org.example.learningsystem.core.security.config.BasicAuthenticationCredentials;
import org.example.learningsystem.core.security.config.UserCredentials;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(PostgreSQLConfiguration.class)
@Testcontainers
@RequiredArgsConstructor
public class CourseEnrollmentTest {

    private static final String ENROLLMENT_URL = "/courses/{id}/students/{studentId}";

    @Autowired
    private BasicAuthenticationCredentials basicAuthenticationCredentials;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    private Course course;

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
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId)
                        .headers(buildHeaders(managerCredentials)))
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
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(post(ENROLLMENT_URL, savedCourse.getId(), savedStudent.getId())
                        .headers(buildHeaders(managerCredentials)))
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
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(post(ENROLLMENT_URL, savedCourse.getId(), savedStudent.getId())
                        .headers(buildHeaders(managerCredentials)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void enrollStudent_givenCourseIdAndStudentId_shouldReturn401() throws Exception {
        // given
        var courseId = UUID.randomUUID();
        var studentId = UUID.randomUUID();

        // when, then
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isUnauthorized());
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
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId)
                        .headers(buildHeaders(managerCredentials)))
                .andExpect(status().isOk());
        mockMvc.perform(delete(ENROLLMENT_URL, courseId, studentId)
                        .headers(buildHeaders(managerCredentials)))
                .andExpect(status().isNoContent());

        // then
        var courseStudents = studentService.getAllByCourseId(courseId);
        assertEquals(0, courseStudents.size());
    }

    @Test
    void unenrollStudent_givenCourseIdAndStudentId_shouldReturn401() throws Exception {
        // given
        var courseId = UUID.randomUUID();
        var studentId = UUID.randomUUID();

        // when, then
        mockMvc.perform(delete(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isUnauthorized());
    }

    private HttpHeaders buildHeaders(UserCredentials userCredentials) {
        var headers = new HttpHeaders();
        headers.setBasicAuth(userCredentials.username(), userCredentials.password());
        return headers;
    }

}
