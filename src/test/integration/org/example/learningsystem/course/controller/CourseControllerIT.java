package org.example.learningsystem.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.learningsystem.config.DataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.config.PostgreSQLConfiguration;
import org.example.learningsystem.course.dto.CourseRequestDto;
import org.example.learningsystem.course.dto.CourseResponseDto;
import org.example.learningsystem.course.service.CourseService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.example.learningsystem.util.CourseRequestDtoUtils.createCreateCourseRequestDto;
import static org.example.learningsystem.util.CourseRequestDtoUtils.createUpdateCourseRequestDto;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.example.learningsystem.util.LessonRequestDtoUtils.createClassroomLessonRequestDto;
import static org.example.learningsystem.util.LessonRequestDtoUtils.createVideoLessonRequestDto;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableConfigurationProperties(DataSourceProperties.class)
@Import(PostgreSQLConfiguration.class)
@Testcontainers
@RequiredArgsConstructor
class CourseControllerIT {

    private static final String COURSES_URL = "/courses";
    private static final String COURSE_URL = "/courses/{courseId}";
    private static final String COURSE_LESSONS_URL = "/courses/{courseId}/lessons";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void create_givenCourseRequestDto_shouldSuccessfullyCreateAndReturn201() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();

        // when, then
        mockMvc.perform(post(COURSES_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value(courseRequestDto.title()))
                .andExpect(jsonPath("$.description").value(courseRequestDto.description()));
    }

    @Test
    @WithMockUser
    void create_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var body = "{}";

        // when, then
        mockMvc.perform(post(COURSES_URL)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn200() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();
        var courseResponseDto = create(courseRequestDto);
        var courseId = courseResponseDto.id();

        // when, then
        mockMvc.perform(get(COURSE_URL, courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId.toString()));

        courseService.deleteById(courseId);
    }

    @Test
    void getById_givenId_shouldReturn401() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();
        var courseResponseDto = create(courseRequestDto);
        var courseId = courseResponseDto.id();

        // when, then
        mockMvc.perform(get(COURSE_URL, courseId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn400() throws Exception {
        // given
        var courseId = "123";

        // when, then
        mockMvc.perform(get(COURSE_URL, courseId))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn404() throws Exception {
        // given
        var courseId = UUID.randomUUID();

        // when, then
        mockMvc.perform(get(COURSE_URL, courseId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getAll_givenPageAndSize_shouldReturn200() throws Exception {
        // given
        var page = 0;
        var size = 1;

        // when, then
        mockMvc.perform(get(COURSES_URL)
                        .queryParam("page", String.valueOf(page))
                        .queryParam("size", String.valueOf(size))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.number").value(page))
                .andExpect(jsonPath("$.page.size").value(size))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @WithMockUser
    void updateById_givenCourseRequestDto_shouldSuccessfullyUpdateAndReturn200() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();
        var courseResponseDto = create(courseRequestDto);
        var courseId = courseResponseDto.id();
        courseRequestDto = createUpdateCourseRequestDto(courseId);

        // when, then
        mockMvc.perform(put(COURSE_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId.toString()))
                .andExpect(jsonPath("$.title").value(courseRequestDto.title()))
                .andExpect(jsonPath("$.description").value(courseRequestDto.description()))
                .andExpect(jsonPath("$.isPublic").value(courseRequestDto.isPublic()));

        courseService.deleteById(courseId);
    }

    @Test
    @WithMockUser
    void updateById_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();
        var courseResponseDto = create(courseRequestDto);
        var courseId = courseResponseDto.id();
        var body = "{\"id\": \"%s\"}".formatted(courseId);

        // when, then
        mockMvc.perform(put(COURSE_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());

        courseService.deleteById(courseId);
    }

    @Test
    @WithMockUser
    void updateById_givenCourseId_shouldReturn404() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();
        var courseId = UUID.randomUUID();

        // when, then
        mockMvc.perform(put(COURSE_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteById_givenId_shouldSuccessfullyDeleteAndReturn204() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();
        var courseResponseDto = create(courseRequestDto);
        var courseId = courseResponseDto.id();

        // when, then
        mockMvc.perform(delete(COURSE_URL, courseId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(COURSE_URL, courseId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void createLesson_givenVideoLessonRequestDto_shouldSuccessfullyCreateLessonAndReturn201() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();
        var courseResponseDto = create(courseRequestDto);
        var courseId = courseResponseDto.id();
        var lessonRequestDto = createVideoLessonRequestDto();

        // when, then
        mockMvc.perform(post(COURSE_LESSONS_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.courseId").value(courseId.toString()));

        courseService.deleteById(courseId);
    }

    @Test
    @WithMockUser
    void createLesson_givenClassroomLessonRequestDto_shouldSuccessfullyCreateLessonAndReturn201() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();
        var courseResponseDto = create(courseRequestDto);
        var courseId = courseResponseDto.id();
        var lessonRequestDto = createClassroomLessonRequestDto();

        // when, then
        mockMvc.perform(post(COURSE_LESSONS_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.courseId").value(courseId.toString()));

        courseService.deleteById(courseId);
    }

    @Test
    @WithMockUser
    void createLesson_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();
        var courseResponseDto = create(courseRequestDto);
        var courseId = courseResponseDto.id();
        var body = "{}";

        // when, then
        mockMvc.perform(post(COURSE_LESSONS_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());

        courseService.deleteById(courseId);
    }

    @Test
    @WithMockUser
    void createLesson_givenLessonRequestDto_shouldReturn404() throws Exception {
        // given
        var courseId = UUID.randomUUID();
        var lessonRequestDto = createClassroomLessonRequestDto();

        // when, then
        mockMvc.perform(post(COURSE_LESSONS_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getLessons_givenCourseIdAndPageAndSize_shouldReturn200() throws Exception {
        // given
        var courseRequestDto = createCreateCourseRequestDto();
        var courseResponseDto = create(courseRequestDto);
        var courseId = courseResponseDto.id();
        var page = 0;
        var size = 1;

        // when, then
        mockMvc.perform(get(COURSE_LESSONS_URL, courseId)
                        .queryParam("page", String.valueOf(page))
                        .queryParam("size", String.valueOf(size))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.number").value(page))
                .andExpect(jsonPath("$.page.size").value(size))
                .andExpect(jsonPath("$.content").isArray());

        courseService.deleteById(courseId);
    }

    private CourseResponseDto create(CourseRequestDto courseRequestDto) throws Exception {
        var response = mockMvc.perform(post(COURSES_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto)))
                .andReturn()
                .getResponse();
        return objectMapper.readValue(response.getContentAsString(), CourseResponseDto.class);
    }

}
