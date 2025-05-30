package org.example.learningsystem.lesson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.config.DataSourceProperties;
import org.example.learningsystem.config.PostgreSQLConfiguration;
import org.example.learningsystem.core.security.config.BasicAuthenticationCredentials;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.lesson.dto.classroomlesson.ClassroomLessonResponseDto;
import org.example.learningsystem.lesson.dto.LessonRequestDto;
import org.example.learningsystem.lesson.dto.videolesson.VideoLessonResponseDto;
import org.example.learningsystem.lesson.service.LessonService;
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

import static org.example.learningsystem.util.CourseTestUtils.buildCourse;
import static org.example.learningsystem.util.LessonTestUtils.createClassroomLessonRequestDto;
import static org.example.learningsystem.util.LessonTestUtils.createClassroomLessonUpdateRequestDto;
import static org.example.learningsystem.util.LessonTestUtils.createVideoLessonRequestDto;
import static org.example.learningsystem.util.LessonTestUtils.createVideoLessonUpdateRequestDto;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableConfigurationProperties(DataSourceProperties.class)
@Import(PostgreSQLConfiguration.class)
@Testcontainers
@RequiredArgsConstructor
class LessonControllerIT {

    private static final String CREATE_LESSON_URL = "/courses/{courseId}/lessons";
    private static final String LESSONS_URL = "/lessons";
    private static final String LESSON_URL = "/lessons/{id}";

    @Autowired
    private BasicAuthenticationCredentials basicAuthenticationCredentials;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CourseService courseService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn200() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var lessonRequestDto = createVideoLessonRequestDto(courseId);
        var lessonResponseDto = create(courseId, lessonRequestDto, VideoLessonResponseDto.class);
        var studentId = lessonResponseDto.getId();

        // when, then
        mockMvc.perform(get(LESSON_URL, studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId.toString()));

        courseService.deleteById(courseId);
        lessonService.deleteById(studentId);
    }

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn400() throws Exception {
        // given
        var lessonId = "123";

        // when, then
        mockMvc.perform(get(LESSON_URL, lessonId))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn404() throws Exception {
        // given
        var lessonId = UUID.randomUUID();

        // when, then
        mockMvc.perform(get(LESSON_URL, lessonId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getAll_givenPageAndSize_shouldReturn200() throws Exception {
        // given
        var page = 0;
        var size = 1;

        // when, then
        mockMvc.perform(get(LESSONS_URL)
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
    void updateById_givenVideoLessonRequestDto_shouldSuccessfullyUpdateAndReturn200() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var lessonRequestDto = createVideoLessonRequestDto(courseId);
        var lessonResponseDto = create(courseId, lessonRequestDto, VideoLessonResponseDto.class);
        var lessonId = lessonResponseDto.getId();
        lessonRequestDto = createVideoLessonUpdateRequestDto(lessonId, courseId);

        // when, then
        mockMvc.perform(put(LESSON_URL, lessonId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lessonId.toString()))
                .andExpect(jsonPath("$.url").value(lessonRequestDto.getUrl()))
                .andExpect(jsonPath("$.platform").value(lessonRequestDto.getPlatform()));

        courseService.deleteById(courseId);
        lessonService.deleteById(lessonId);
    }

    @Test
    @WithMockUser
    void updateById_givenClassroomLessonRequestDto_shouldSuccessfullyUpdateAndReturn200() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var lessonRequestDto = createClassroomLessonRequestDto(courseId);
        var lessonResponseDto = create(courseId, lessonRequestDto, ClassroomLessonResponseDto.class);
        var lessonId = lessonResponseDto.getId();
        lessonRequestDto = createClassroomLessonUpdateRequestDto(lessonId, courseId);

        // when, then
        mockMvc.perform(put(LESSON_URL, lessonId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lessonId.toString()))
                .andExpect(jsonPath("$.location").value(lessonRequestDto.getLocation()))
                .andExpect(jsonPath("$.capacity").value(lessonRequestDto.getCapacity()));

        courseService.deleteById(courseId);
        lessonService.deleteById(lessonId);
    }

    @Test
    @WithMockUser
    void updateById_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var lessonRequestDto = createClassroomLessonRequestDto(courseId);
        var lessonResponseDto = create(courseId, lessonRequestDto, ClassroomLessonResponseDto.class);
        var lessonId = lessonResponseDto.getId();
        var body = "{\"id\": \"%s\"}".formatted(lessonId);

        // when, then
        mockMvc.perform(put(LESSON_URL, lessonId)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());

        courseService.deleteById(courseId);
        lessonService.deleteById(lessonId);
    }

    @Test
    @WithMockUser
    void updateById_givenLessonId_shouldReturn404() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var lessonRequestDto = createVideoLessonRequestDto(courseId);
        var lessonId = UUID.randomUUID();

        // when, then
        mockMvc.perform(put(LESSON_URL, lessonId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDto)))
                .andExpect(status().isNotFound());

        courseService.deleteById(courseId);
    }

    @Test
    @WithMockUser
    void deleteById_givenId_shouldSuccessfullyDeleteAndReturn204() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var lessonRequestDto = createClassroomLessonRequestDto(courseId);
        var lessonResponseDto = create(courseId, lessonRequestDto, ClassroomLessonResponseDto.class);
        var lessonId = lessonResponseDto.getId();

        // when, then
        mockMvc.perform(delete(LESSON_URL, lessonId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(LESSON_URL, lessonId))
                .andExpect(status().isNotFound());

        courseService.deleteById(courseId);
    }

    private <T> T create(UUID courseId, LessonRequestDto lessonRequestDto, Class<T> responseType) throws Exception {
        var response = mockMvc.perform(post(CREATE_LESSON_URL, courseId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDto)))
                .andReturn()
                .getResponse();
        return objectMapper.readValue(response.getContentAsString(), responseType);
    }

    @WithMockUser
    private Course createCourse() {
        var course = buildCourse();
        return courseService.create(course);
    }

}
