package org.example.learningsystem.course.controller;

import org.example.learningsystem.config.DataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.config.PostgreSQLConfiguration;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.student.model.Student;
import org.example.learningsystem.student.service.StudentService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.example.learningsystem.util.CourseTestUtils.buildCourse;
import static org.example.learningsystem.util.StudentTestUtils.buildStudent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableConfigurationProperties(DataSourceProperties.class)
@Import(PostgreSQLConfiguration.class)
@Testcontainers
@RequiredArgsConstructor
class CourseEnrollmentControllerIT {

    private static final String ENROLLMENT_URL = "/courses/{id}/students/{studentId}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;

    @Test
    @WithMockUser
    void enrollStudent_givenCourseAndStudent_shouldSuccessfullyEnrollStudentAndReturn200() throws Exception {
        // given
        var course = buildCourse();
        var savedCourse = courseService.create(course);
        var courseId = savedCourse.getId();

        var student = buildStudentWithCoins(course.getPrice());
        var savedStudent = studentService.create(student);
        var studentId = savedStudent.getId();
        var pageable = PageRequest.of(0, 1);

        // when
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isOk());

        // then
        var courseStudents = studentService.getAllByCourseId(courseId, pageable);
        assertEquals(1, courseStudents.getTotalElements());
        var updatedStudent = courseStudents.getContent().getFirst();
        assertEquals(studentId, updatedStudent.getId());

        assertNotEquals(student.getCoins(), updatedStudent.getCoins());
        var updatedCourse = courseService.getById(courseId);
        assertNotEquals(course.getCoinsPaid(), updatedCourse.getCoinsPaid());

        cleanUp(courseId, studentId);
    }

    @Test
    @WithMockUser
    void enrollStudent_givenCourseAndStudent_shouldThrowEnrollmentDeniedExceptionAndReturn500() throws Exception {
        // given
        var course = buildPrivateCourse();
        var savedCourse = courseService.create(course);
        var courseId = savedCourse.getId();

        var student = buildStudentWithCoins(course.getPrice());
        var savedStudent = studentService.create(student);
        var studentId = savedStudent.getId();

        // when, then
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isInternalServerError());

        cleanUp(courseId, studentId);
    }

    @Test
    @WithMockUser
    void enrollStudent_givenCourseAndStudent_shouldThrowInsufficientFundsExceptionAndReturn500() throws Exception {
        // given
        var course = buildPrivateCourse();
        var savedCourse = courseService.create(course);
        var courseId = savedCourse.getId();

        var student = buildStudentWithCoins(BigDecimal.ZERO);
        var savedStudent = studentService.create(student);
        var studentId = savedStudent.getId();

        // when, then
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isInternalServerError());

        cleanUp(courseId, studentId);
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
    @WithMockUser
    void enrollStudent_givenCourseAndStudentId_shouldThrowEntityNotFoundExceptionAndReturn404() throws Exception {
        // given
        var course = buildPrivateCourse();
        var savedCourse = courseService.create(course);
        var courseId = savedCourse.getId();
        var studentId = UUID.randomUUID();

        // when, then
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isNotFound());

        courseService.deleteById(courseId);
    }

    @Test
    @WithMockUser
    void enrollStudent_givenCourseIdAndStudent_shouldThrowEntityNotFoundExceptionAndReturn404() throws Exception {
        // given
        var courseId = UUID.randomUUID();
        var student = buildStudentWithCoins(BigDecimal.ZERO);
        var savedStudent = studentService.create(student);
        var studentId = savedStudent.getId();

        // when, then
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isNotFound());

        studentService.deleteById(studentId);
    }

    @Test
    @WithMockUser
    void unenrollStudent_givenCourseAndStudent_shouldSuccessfullyUnenrollStudentAndReturn204() throws Exception {
        // given
        var course = buildCourse();
        var savedCourse = courseService.create(course);
        var courseId = savedCourse.getId();

        var student = buildStudentWithCoins(course.getPrice());
        var savedStudent = studentService.create(student);
        var studentId = savedStudent.getId();
        var pageable = PageRequest.of(0, 10);

        // when
        mockMvc.perform(delete(ENROLLMENT_URL, courseId, studentId))
                .andExpect(status().isNoContent());

        // then
        var courseStudents = studentService.getAllByCourseId(courseId, pageable);
        assertEquals(0, courseStudents.getTotalElements());

        cleanUp(courseId, studentId);
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

    private Student buildStudentWithCoins(BigDecimal coins) {
        var student = buildStudent();
        student.setCoins(coins);
        return student;
    }

    private Course buildPrivateCourse() {
        var course = buildCourse();
        var settings = course.getSettings();
        settings.setIsPublic(false);
        return course;
    }

    private void cleanUp(UUID courseId, UUID studentId) {
        courseService.deleteById(courseId);
        studentService.deleteById(studentId);
    }

}
