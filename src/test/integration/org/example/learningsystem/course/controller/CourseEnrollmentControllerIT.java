package org.example.learningsystem.course.controller;

import org.example.learningsystem.AbstractCommonIT;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.student.model.Student;
import org.example.learningsystem.student.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static org.example.learningsystem.course.common.builder.CourseEnrollmentRequestBuilder.buildEnrollRequest;
import static org.example.learningsystem.course.common.builder.CourseEnrollmentRequestBuilder.buildUnenrollRequest;
import static org.example.learningsystem.course.common.builder.CourseBuilderIT.buildCourse;
import static org.example.learningsystem.student.common.builder.StudentBuilderIT.buildStudent;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@WithMockUser
class CourseEnrollmentControllerIT extends AbstractCommonIT {

    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;

    @Test
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
        var request = buildEnrollRequest(courseId, studentId);
        mockMvc.perform(request)
                .andExpect(status().isOk());

        // then
        var courseStudentsPage = studentService.getAllByCourseId(courseId, pageable);
        var courseStudents = courseStudentsPage.getContent();
        var updatedStudent = courseStudents.getFirst();
        var updatedCourse = courseService.getById(courseId);

        assertAll(
                () -> assertEquals(1, courseStudentsPage.getTotalElements()),
                () -> assertEquals(studentId, updatedStudent.getId()),
                () -> assertNotEquals(student.getCoins(), updatedStudent.getCoins()),
                () -> assertNotEquals(course.getCoinsPaid(), updatedCourse.getCoinsPaid())
        );

        cleanUp(courseId, studentId);
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldThrowEnrollmentDeniedExceptionAndReturn500() throws Exception {
        // given
        var course = buildPrivateCourse();
        var savedCourse = courseService.create(course);
        var courseId = savedCourse.getId();

        var student = buildStudentWithCoins(course.getPrice());
        var savedStudent = studentService.create(student);
        var studentId = savedStudent.getId();

        // when, then
        var request = buildEnrollRequest(courseId, studentId);
        mockMvc.perform(request)
                .andExpect(status().isInternalServerError());

        cleanUp(courseId, studentId);
    }

    @Test
    void enrollStudent_givenCourseAndStudent_shouldThrowInsufficientFundsExceptionAndReturn500() throws Exception {
        // given
        var course = buildPrivateCourse();
        var savedCourse = courseService.create(course);
        var courseId = savedCourse.getId();

        var student = buildStudentWithCoins(BigDecimal.ZERO);
        var savedStudent = studentService.create(student);
        var studentId = savedStudent.getId();

        // when, then
        var request = buildEnrollRequest(courseId, studentId);
        mockMvc.perform(request)
                .andExpect(status().isInternalServerError());

        cleanUp(courseId, studentId);
    }

    @Test
    @WithAnonymousUser
    void enrollStudent_givenCourseIdAndStudentId_shouldReturn401() throws Exception {
        // given
        var courseId = UUID.randomUUID();
        var studentId = UUID.randomUUID();

        // when, then
        var request = buildEnrollRequest(courseId, studentId);
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    void enrollStudent_givenCourseAndStudentId_shouldThrowEntityNotFoundExceptionAndReturn404() throws Exception {
        // given
        var course = buildPrivateCourse();
        var savedCourse = courseService.create(course);
        var courseId = savedCourse.getId();

        var studentId = UUID.randomUUID();

        // when, then
        var request = buildEnrollRequest(courseId, studentId);
        mockMvc.perform(request)
                .andExpect(status().isNotFound());

        cleanUp(courseId, studentId);
    }

    @Test
    void enrollStudent_givenCourseIdAndStudent_shouldThrowEntityNotFoundExceptionAndReturn404() throws Exception {
        // given
        var courseId = UUID.randomUUID();

        var student = buildStudentWithCoins(BigDecimal.ZERO);
        var savedStudent = studentService.create(student);
        var studentId = savedStudent.getId();

        // when, then
        var request = buildEnrollRequest(courseId, studentId);
        mockMvc.perform(request)
                .andExpect(status().isNotFound());

        cleanUp(courseId, studentId);
    }

    @Test
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
        var request = buildUnenrollRequest(courseId, studentId);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        // then
        var courseStudents = studentService.getAllByCourseId(courseId, pageable);
        assertEquals(0, courseStudents.getTotalElements());

        cleanUp(courseId, studentId);
    }

    @Test
    @WithAnonymousUser
    void unenrollStudent_givenCourseIdAndStudentId_shouldReturn401() throws Exception {
        // given
        var courseId = UUID.randomUUID();
        var studentId = UUID.randomUUID();

        // when, then
        var request = buildUnenrollRequest(courseId, studentId);
        mockMvc.perform(request)
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
        if (nonNull(courseId)) {
            courseService.deleteById(courseId);
        }
        if (nonNull(studentId)) {
            studentService.deleteById(studentId);
        }
    }

}
