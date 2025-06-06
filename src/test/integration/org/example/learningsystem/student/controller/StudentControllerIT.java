package org.example.learningsystem.student.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.AbstractCommonIT;
import org.example.learningsystem.core.exception.model.EntityNotFoundException;
import org.example.learningsystem.student.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static org.example.learningsystem.student.common.builder.StudentRequestBuilder.buildCreateRequest;
import static org.example.learningsystem.student.common.builder.StudentRequestBuilder.buildDeleteByIdRequest;
import static org.example.learningsystem.student.common.builder.StudentRequestBuilder.buildGetAllRequest;
import static org.example.learningsystem.student.common.builder.StudentRequestBuilder.buildGetByIdRequest;
import static org.example.learningsystem.student.common.builder.StudentRequestBuilder.buildUpdateByIdRequest;
import static org.example.learningsystem.student.common.builder.StudentBuilderIT.buildCreateStudentRequestDto;
import static org.example.learningsystem.student.common.builder.StudentBuilderIT.buildStudent;
import static org.example.learningsystem.student.common.builder.StudentBuilderIT.buildUpdateStudentRequestDto;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@WithMockUser
class StudentControllerIT extends AbstractCommonIT {

    @Autowired
    private StudentService studentService;

    @Test
    void create_givenStudentRequestDto_shouldSuccessfullyCreateAndReturn201() throws Exception {
        // given
        var requestDto = buildCreateStudentRequestDto();
        var content = objectMapper.writeValueAsString(requestDto);

        // when, then
        var request = buildCreateRequest(content);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.firstName").value(requestDto.firstName()),
                        jsonPath("$.lastName").value(requestDto.lastName())
                );
    }

    @Test
    void create_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var content = "{}";

        // when, then
        var request = buildCreateRequest(content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_givenId_shouldReturn200() throws Exception {
        // given
        var id = createStudentAndGetId();

        // when, then
        var request = buildGetByIdRequest(id);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        studentService.deleteById(id);
    }

    @Test
    @WithAnonymousUser
    void getById_givenId_shouldReturn401() throws Exception {
        // given
        var id = UUID.randomUUID();

        // when, then
        var request = buildGetByIdRequest(id);
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getById_givenId_shouldReturn404() throws Exception {
        // given
        var id = UUID.randomUUID();

        // when, then
        var request = buildGetByIdRequest(id);
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_givenPageAndSize_shouldReturn200() throws Exception {
        // given
        var page = 0;
        var size = 1;
        var pageRequest = PageRequest.of(page, size);

        // when, then
        var request = buildGetAllRequest(pageRequest);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page.number").value(page),
                        jsonPath("$.page.size").value(size),
                        jsonPath("$.content").isArray()
                );
    }

    @Test
    void updateById_givenStudentRequestDto_shouldSuccessfullyUpdateAndReturn200() throws Exception {
        // given
        var id = createStudentAndGetId();

        var requestDto = buildUpdateStudentRequestDto(id);
        var content = objectMapper.writeValueAsString(requestDto);

        // when, then
        var request = buildUpdateByIdRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(id.toString()),
                        jsonPath("$.firstName").value(requestDto.firstName()),
                        jsonPath("$.lastName").value(requestDto.lastName())
                );

        studentService.deleteById(id);
    }

    @Test
    void updateById_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var id = createStudentAndGetId();

        var content = "{\"id\": \"%s\"}".formatted(id);

        // when, then
        var request = buildUpdateByIdRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

        studentService.deleteById(id);
    }

    @Test
    void updateById_givenCourseId_shouldReturn404() throws Exception {
        // given
        var requestDto = buildCreateStudentRequestDto();
        var id = UUID.randomUUID();
        var content = objectMapper.writeValueAsString(requestDto);

        // when, then
        var request = buildUpdateByIdRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById_givenId_shouldSuccessfullyDeleteAndReturn204() throws Exception {
        // given
        var id = createStudentAndGetId();

        // when, then
        var deleteRequest = buildDeleteByIdRequest(id);
        mockMvc.perform(deleteRequest)
                .andExpect(status().isNoContent());

        assertThrows(EntityNotFoundException.class, () -> studentService.getById(id));
    }

    private UUID createStudentAndGetId() {
        var student = buildStudent();
        var savedStudent = studentService.create(student);
        return savedStudent.getId();
    }
}
