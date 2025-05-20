package org.example.learningsystem.course;

import org.example.learningsystem.LearningSystemApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {LearningSystemApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class SecurityTest {

    private static final String ENROLLMENT_URL = "/courses/{id}/students/{studentId}";
    private static final String USERNAME = "manager@gmail.com";
    private static final String PASSWORD = "manager";
    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:17.4");

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @Test
    void enrollStudent_givenCourseIdAndStudentId_shouldSuccessfullyAuthenticateAndReturn404() throws Exception {
        // given
        var courseId = UUID.randomUUID();
        var studentId = UUID.randomUUID();

        // when, then
        mockMvc.perform(post(ENROLLMENT_URL, courseId, studentId)
                        .headers(buildHeaders()))
                .andExpect(status().isNotFound());
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
    void unenrollStudent_givenCourseIdAndStudentId_shouldSuccessfullyUnenrollStudentAndReturn204() throws Exception {
        // given
        var courseId = UUID.randomUUID();
        var studentId = UUID.randomUUID();

        // when, then
        mockMvc.perform(delete(ENROLLMENT_URL, courseId, studentId)
                        .headers(buildHeaders()))
                .andExpect(status().isNoContent());
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

    private HttpHeaders buildHeaders() {
        var headers = new HttpHeaders();
        headers.setBasicAuth(USERNAME, PASSWORD);
        return headers;
    }

}
