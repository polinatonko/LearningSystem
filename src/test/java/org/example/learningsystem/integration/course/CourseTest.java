package org.example.learningsystem.integration.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.config.PostgreSQLConfiguration;
import org.example.learningsystem.core.security.config.BasicAuthenticationCredentials;
import org.example.learningsystem.course.dto.CourseRequestDto;
import org.example.learningsystem.course.dto.CourseResponseDto;
import org.example.learningsystem.course.service.CourseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static java.util.Objects.nonNull;
import static org.example.learningsystem.util.CourseTestUtils.createCreateCourseRequestDto;
import static org.example.learningsystem.util.CourseTestUtils.COINS_PAID;
import static org.example.learningsystem.util.CourseTestUtils.DESCRIPTION;
import static org.example.learningsystem.util.CourseTestUtils.END_DATE;
import static org.example.learningsystem.util.CourseTestUtils.IS_PUBLIC;
import static org.example.learningsystem.util.CourseTestUtils.PRICE;
import static org.example.learningsystem.util.CourseTestUtils.START_DATE;
import static org.example.learningsystem.util.CourseTestUtils.TITLE;
import static org.example.learningsystem.util.LessonTestUtils.createClassroomLessonRequestDto;
import static org.example.learningsystem.util.LessonTestUtils.createVideoLessonRequestDto;
import static org.example.learningsystem.util.TestUtils.buildBasicAuthenticationHeader;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(PostgreSQLConfiguration.class)
@Testcontainers
@RequiredArgsConstructor
class CourseTest {

    private static final String COURSES_URL = "/courses";
    private static final String COURSE_URL = "/courses/{courseId}";
    private static final String COURSE_LESSONS_URL = "/courses/{courseId}/lessons";

    @Autowired
    private BasicAuthenticationCredentials basicAuthenticationCredentials;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ObjectMapper objectMapper;
    private CourseRequestDto courseRequestDto;
    private CourseResponseDto courseResponseDto;

    @BeforeEach
    void setup() throws Exception {
        courseRequestDto = createCreateCourseRequestDto();
        var managerCredentials = basicAuthenticationCredentials.getManager();
        var response = mockMvc.perform(post(COURSES_URL)
                        .headers(buildBasicAuthenticationHeader(managerCredentials))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto)))
                .andReturn()
                .getResponse();
        courseResponseDto = objectMapper.readValue(response.getContentAsString(), CourseResponseDto.class);
    }

    @AfterEach
    void cleanup() {
        var courseId = courseResponseDto.id();
        if (nonNull(courseId)) {
            courseService.deleteById(courseId);
        }
    }

    @Test
    void create_givenCourseRequestDto_shouldSuccessfullyCreateAndReturn201() throws Exception {
        // given
        courseRequestDto = createCreateCourseRequestDto();
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(post(COURSES_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto))
                        .headers(buildBasicAuthenticationHeader(managerCredentials)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value(courseRequestDto.title()))
                .andExpect(jsonPath("$.description").value(courseRequestDto.description()));
    }

    @Test
    void create_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var managerCredentials = basicAuthenticationCredentials.getManager();
        var body = "{}";

        // when, then
        mockMvc.perform(post(COURSES_URL)
                        .contentType(APPLICATION_JSON)
                        .content(body)
                        .headers(buildBasicAuthenticationHeader(managerCredentials)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getById_givenId_shouldReturn200() throws Exception {
        // given
        var courseId = courseResponseDto.id();
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(get(COURSE_URL, courseId)
                .headers(buildBasicAuthenticationHeader(managerCredentials)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId.toString()));
    }

    @Test
    void getById_givenId_shouldReturn401() throws Exception {
        // given
        var courseId = courseResponseDto.id();

        // when, then
        mockMvc.perform(get(COURSE_URL, courseId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getById_givenId_shouldReturn400() throws Exception {
        // given
        var courseId = "123";
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(get(COURSE_URL, courseId)
                        .headers(buildBasicAuthenticationHeader(managerCredentials)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getById_givenId_shouldReturn404() throws Exception {
        // given
        var courseId = UUID.randomUUID();
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(get(COURSE_URL, courseId)
                        .headers(buildBasicAuthenticationHeader(managerCredentials)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_givenPageAndSize_shouldReturn200() throws Exception {
        // given
        var page = 0;
        var size = 1;
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(get(COURSES_URL)
                        .headers(buildBasicAuthenticationHeader(managerCredentials))
                        .queryParam("page", String.valueOf(page))
                        .queryParam("size", String.valueOf(size))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void updateById_givenCourseRequestDto_shouldSuccessfullyUpdateAndReturn200() throws Exception {
        // given
        var courseId = courseResponseDto.id();
        courseRequestDto = new CourseRequestDto(
                courseId,
                TITLE,
                DESCRIPTION,
                PRICE,
                COINS_PAID,
                START_DATE,
                END_DATE,
                IS_PUBLIC
        );
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(put(COURSE_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto))
                        .headers(buildBasicAuthenticationHeader(managerCredentials)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId.toString()))
                .andExpect(jsonPath("$.title").value(courseRequestDto.title()))
                .andExpect(jsonPath("$.description").value(courseRequestDto.description()))
                .andExpect(jsonPath("$.isPublic").value(courseRequestDto.isPublic()));
    }

    @Test
    void updateById_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var managerCredentials = basicAuthenticationCredentials.getManager();
        var courseId = courseResponseDto.id();
        var body = "{\"id\": \"%s\"}".formatted(courseId);

        // when, then
        mockMvc.perform(put(COURSE_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(body)
                        .headers(buildBasicAuthenticationHeader(managerCredentials)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateById_givenCourseId_shouldReturn404() throws Exception {
        // given
        var managerCredentials = basicAuthenticationCredentials.getManager();
        var courseId = UUID.randomUUID();
        courseRequestDto = createCreateCourseRequestDto();

        // when, then
        mockMvc.perform(put(COURSE_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto))
                        .headers(buildBasicAuthenticationHeader(managerCredentials)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById_givenId_shouldSuccessfullyDeleteAndReturn204() throws Exception {
        // given
        var courseId = courseResponseDto.id();
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(delete(COURSE_URL, courseId)
                .headers(buildBasicAuthenticationHeader(managerCredentials)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(COURSE_URL, courseId)
                .headers(buildBasicAuthenticationHeader(managerCredentials)))
                .andExpect(status().isNotFound());
    }

    @Test
    void createLesson_givenVideoLessonRequestDto_shouldSuccessfullyCreateLessonAndReturn201() throws Exception {
        // given
        var courseId = courseResponseDto.id();
        var lessonRequestDto = createVideoLessonRequestDto();
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(post(COURSE_LESSONS_URL, courseId)
                .headers(buildBasicAuthenticationHeader(managerCredentials))
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lessonRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.courseId").value(courseId.toString()));
    }

    @Test
    void createLesson_givenClassroomLessonRequestDto_shouldSuccessfullyCreateLessonAndReturn201() throws Exception {
        // given
        var courseId = courseResponseDto.id();
        var lessonRequestDto = createClassroomLessonRequestDto();
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(post(COURSE_LESSONS_URL, courseId)
                        .headers(buildBasicAuthenticationHeader(managerCredentials))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.courseId").value(courseId.toString()));
    }

    @Test
    void createLesson_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var courseId = courseResponseDto.id();
        var body = "{}";
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(post(COURSE_LESSONS_URL, courseId)
                        .headers(buildBasicAuthenticationHeader(managerCredentials))
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createLesson_givenLessonRequestDto_shouldReturn404() throws Exception {
        // given
        var courseId = UUID.randomUUID();
        var lessonRequestDto = createClassroomLessonRequestDto();
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(post(COURSE_LESSONS_URL, courseId)
                        .headers(buildBasicAuthenticationHeader(managerCredentials))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getLessons_givenCourseIdAndPageAndSize_shouldReturn200() throws Exception {
        // given
        var courseId = courseResponseDto.id();
        var page = 0;
        var size = 1;
        var managerCredentials = basicAuthenticationCredentials.getManager();

        // when, then
        mockMvc.perform(get(COURSE_LESSONS_URL, courseId)
                        .headers(buildBasicAuthenticationHeader(managerCredentials))
                        .queryParam("page", String.valueOf(page))
                        .queryParam("size", String.valueOf(size))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.content").isArray());
    }

}
