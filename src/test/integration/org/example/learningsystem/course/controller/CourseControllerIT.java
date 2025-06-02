package org.example.learningsystem.course.controller;

import org.example.learningsystem.common.AbstractCommonIT;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.exception.model.EntityNotFoundException;
import org.example.learningsystem.course.service.CourseService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.UUID;

import static org.example.learningsystem.common.builder.CourseRequestBuilder.buildCreateRequest;
import static org.example.learningsystem.common.builder.CourseRequestBuilder.buildCreateLessonRequest;
import static org.example.learningsystem.common.builder.CourseRequestBuilder.buildDeleteByIdRequest;
import static org.example.learningsystem.common.builder.CourseRequestBuilder.buildGetAllRequest;
import static org.example.learningsystem.common.builder.CourseRequestBuilder.buildGetByIdRequest;
import static org.example.learningsystem.common.builder.CourseRequestBuilder.buildGetLessonsByIdRequest;
import static org.example.learningsystem.common.builder.CourseRequestBuilder.buildUpdateByIdRequest;
import static org.example.learningsystem.common.util.CourseUtilsIT.buildCourse;
import static org.example.learningsystem.common.util.CourseUtilsIT.buildCreateCourseRequestDto;
import static org.example.learningsystem.common.util.CourseUtilsIT.buildUpdateCourseRequestDto;
import static org.example.learningsystem.common.util.LessonUtilsIT.createClassroomLessonRequestDto;
import static org.example.learningsystem.common.util.LessonUtilsIT.createVideoLessonRequestDto;
import static org.junit.Assert.assertThrows;
import static org.springframework.data.domain.Sort.Order;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@RequiredArgsConstructor
class CourseControllerIT extends AbstractCommonIT {

    @Autowired
    private CourseService courseService;

    @Test
    @WithMockUser
    void create_givenCourseRequestDto_shouldSuccessfullyCreateAndReturn201() throws Exception {
        // given
        var requestDto = buildCreateCourseRequestDto();
        var content = objectMapper.writeValueAsString(requestDto);

        // when, then
        var request = buildCreateRequest(content);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.title").value(requestDto.title()),
                        jsonPath("$.description").value(requestDto.description())
                );
    }

    @Test
    @WithMockUser
    void create_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var content = "{}";

        // when, then
        var request = buildCreateRequest(content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn200() throws Exception {
        // given
        var id = createAndGetId();

        // when, then
        var request = buildGetByIdRequest(id);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        courseService.deleteById(id);
    }

    @Test
    void getById_givenId_shouldReturn401() throws Exception {
        // given
        var id = UUID.randomUUID();

        // when, then
        var request = buildGetByIdRequest(id);
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getById_givenId_shouldReturn404() throws Exception {
        // given
        var id = UUID.randomUUID();

        // when, then
        var request = buildGetByIdRequest(id);
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getAll_givenPageAndSize_shouldReturn200() throws Exception {
        // given
        var page = 0;
        var size = 1;
        var orders = List.of(Order.asc("id"), Order.desc("title"));
        var sort = Sort.by(orders);
        var pageRequest = PageRequest.of(page, size, sort);

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
    @WithMockUser
    void updateById_givenCourseRequestDto_shouldSuccessfullyUpdateAndReturn200() throws Exception {
        // given
        var id = createAndGetId();

        var requestDto = buildUpdateCourseRequestDto(id);
        var content = objectMapper.writeValueAsString(requestDto);

        // when, then
        var request = buildUpdateByIdRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(id.toString()),
                        jsonPath("$.title").value(requestDto.title()),
                        jsonPath("$.description").value(requestDto.description()),
                        jsonPath("$.isPublic").value(requestDto.isPublic())
                );

        courseService.deleteById(id);
    }

    @Test
    @WithMockUser
    void updateById_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var id = createAndGetId();

        var content = "{\"id\": \"%s\"}".formatted(id);

        // when, then
        var request = buildUpdateByIdRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

        courseService.deleteById(id);
    }

    @Test
    @WithMockUser
    void updateById_givenCourseId_shouldReturn404() throws Exception {
        // given
        var id = UUID.randomUUID();

        var requestDto = buildCreateCourseRequestDto();
        var content = objectMapper.writeValueAsString(requestDto);

        // when, then
        var request = buildUpdateByIdRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteById_givenId_shouldSuccessfullyDeleteAndReturn204() throws Exception {
        // given
        var id = createAndGetId();

        // when, then
        var request = buildDeleteByIdRequest(id);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        assertThrows(EntityNotFoundException.class, () -> courseService.getById(id));
    }

    @Test
    @WithMockUser
    void createLesson_givenVideoLessonRequestDto_shouldSuccessfullyCreateLessonAndReturn201() throws Exception {
        // given
        var id = createAndGetId();

        var lessonRequestDto = createVideoLessonRequestDto(id);
        var content = objectMapper.writeValueAsString(lessonRequestDto);

        // when, then
        var request = buildCreateLessonRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.courseId").value(id.toString()));

        courseService.deleteById(id);
    }

    @Test
    @WithMockUser
    void createLesson_givenClassroomLessonRequestDto_shouldSuccessfullyCreateLessonAndReturn201() throws Exception {
        // given
        var id = createAndGetId();

        var lessonRequestDto = createClassroomLessonRequestDto(id);
        var content = objectMapper.writeValueAsString(lessonRequestDto);

        // when, then
        var request = buildCreateLessonRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.courseId").value(id.toString())
                );

        courseService.deleteById(id);
    }

    @Test
    @WithMockUser
    void createLesson_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var id = createAndGetId();

        var content = "{}";

        // when, then
        var request = buildCreateLessonRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

        courseService.deleteById(id);
    }

    @Test
    @WithMockUser
    void createLesson_givenLessonRequestDto_shouldReturn404() throws Exception {
        // given
        var id = UUID.randomUUID();

        var lessonRequestDto = createClassroomLessonRequestDto(id);
        var content = objectMapper.writeValueAsString(lessonRequestDto);

        // when, then
        var request = buildCreateLessonRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getLessons_givenCourseIdAndPageAndSize_shouldReturn200() throws Exception {
        // given
        var id = createAndGetId();

        var page = 0;
        var size = 1;
        var pageRequest = PageRequest.of(page, size);

        // when, then
        var request = buildGetLessonsByIdRequest(id, pageRequest);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page.number").value(page),
                        jsonPath("$.page.size").value(size),
                        jsonPath("$.content").isArray()
                );

        courseService.deleteById(id);
    }

    private UUID createAndGetId() {
        var course = buildCourse();
        var savedCourse = courseService.create(course);
        return savedCourse.getId();
    }

}
