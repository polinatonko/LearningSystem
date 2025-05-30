package org.example.learningsystem.student.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.config.DataSourceProperties;
import org.example.learningsystem.config.PostgreSQLConfiguration;
import org.example.learningsystem.student.dto.StudentRequestDto;
import org.example.learningsystem.student.dto.StudentResponseDto;
import org.example.learningsystem.student.service.StudentService;
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

import static org.example.learningsystem.util.StudentTestUtils.buildCreateStudentRequestDto;
import static org.example.learningsystem.util.StudentTestUtils.buildUpdateStudentRequestDto;
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
class StudentControllerIT {

    private static final String STUDENTS_URL = "/students";
    private static final String STUDENT_URL = "/students/{id}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void create_givenStudentRequestDto_shouldSuccessfullyCreateAndReturn201() throws Exception {
        // given
        var studentRequestDto = buildCreateStudentRequestDto();

        // when, then
        mockMvc.perform(post(STUDENTS_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(studentRequestDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(studentRequestDto.lastName()));
    }

    @Test
    @WithMockUser
    void create_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var body = "{}";

        // when, then
        mockMvc.perform(post(STUDENTS_URL)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn200() throws Exception {
        // given
        var studentRequestDto = buildCreateStudentRequestDto();
        var studentResponseDto = create(studentRequestDto);
        var studentId = studentResponseDto.id();

        // when, then
        mockMvc.perform(get(STUDENT_URL, studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId.toString()));

        studentService.deleteById(studentId);
    }

    @Test
    void getById_givenId_shouldReturn401() throws Exception {
        // given
        var studentRequestDto = buildCreateStudentRequestDto();
        var studentResponseDto = create(studentRequestDto);
        var studentId = studentResponseDto.id();

        // when, then
        mockMvc.perform(get(STUDENT_URL, studentId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn400() throws Exception {
        // given
        var studentId = "123";

        // when, then
        mockMvc.perform(get(STUDENT_URL, studentId))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn404() throws Exception {
        // given
        var studentId = UUID.randomUUID();

        // when, then
        mockMvc.perform(get(STUDENT_URL, studentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getAll_givenPageAndSize_shouldReturn200() throws Exception {
        // given
        var page = 0;
        var size = 1;

        // when, then
        mockMvc.perform(get(STUDENTS_URL)
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
    void updateById_givenStudentRequestDto_shouldSuccessfullyUpdateAndReturn200() throws Exception {
        // given
        var studentRequestDto = buildCreateStudentRequestDto();
        var studentResponseDto = create(studentRequestDto);
        var studentId = studentResponseDto.id();
        studentRequestDto = buildUpdateStudentRequestDto(studentId);

        // when, then
        mockMvc.perform(put(STUDENT_URL, studentId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId.toString()))
                .andExpect(jsonPath("$.firstName").value(studentRequestDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(studentRequestDto.lastName()));

        studentService.deleteById(studentId);
    }

    @Test
    @WithMockUser
    void updateById_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var studentRequestDto = buildCreateStudentRequestDto();
        var studentResponseDto = create(studentRequestDto);
        var studentId = studentResponseDto.id();
        var body = "{\"id\": \"%s\"}".formatted(studentId);

        // when, then
        mockMvc.perform(put(STUDENT_URL, studentId)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());

        studentService.deleteById(studentId);
    }

    @Test
    @WithMockUser
    void updateById_givenCourseId_shouldReturn404() throws Exception {
        // given
        var studentRequestDto = buildCreateStudentRequestDto();
        var studentId = UUID.randomUUID();

        // when, then
        mockMvc.perform(put(STUDENT_URL, studentId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteById_givenId_shouldSuccessfullyDeleteAndReturn204() throws Exception {
        // given
        var studentRequestDto = buildCreateStudentRequestDto();
        var studentResponseDto = create(studentRequestDto);
        var studentId = studentResponseDto.id();

        // when, then
        mockMvc.perform(delete(STUDENT_URL, studentId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(STUDENT_URL, studentId))
                .andExpect(status().isNotFound());
    }

    private StudentResponseDto create(StudentRequestDto studentRequestDto) throws Exception {
        var response = mockMvc.perform(post(STUDENTS_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequestDto)))
                .andReturn()
                .getResponse();
        return objectMapper.readValue(response.getContentAsString(), StudentResponseDto.class);
    }

}
