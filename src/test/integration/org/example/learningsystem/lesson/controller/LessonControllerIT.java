package org.example.learningsystem.lesson.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.AbstractCommonIT;
import org.example.learningsystem.core.exception.model.EntityNotFoundException;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.lesson.classroom.model.ClassroomLesson;
import org.example.learningsystem.lesson.video.model.VideoLesson;
import org.example.learningsystem.lesson.common.service.LessonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static org.example.learningsystem.lesson.common.builder.LessonRequestBuilder.buildDeleteByIdRequest;
import static org.example.learningsystem.lesson.common.builder.LessonRequestBuilder.buildGetAllRequest;
import static org.example.learningsystem.lesson.common.builder.LessonRequestBuilder.buildGetByIdRequest;
import static org.example.learningsystem.lesson.common.builder.LessonRequestBuilder.buildUpdateByIdRequest;
import static org.example.learningsystem.course.common.builder.CourseBuilderIT.buildCourse;
import static org.example.learningsystem.lesson.common.builder.LessonBuilderIT.buildClassroomLesson;
import static org.example.learningsystem.lesson.common.builder.LessonBuilderIT.buildVideoLesson;
import static org.example.learningsystem.lesson.common.builder.LessonBuilderIT.createClassroomLessonUpdateRequestDto;
import static org.example.learningsystem.lesson.common.builder.LessonBuilderIT.createVideoLessonRequestDto;
import static org.example.learningsystem.lesson.common.builder.LessonBuilderIT.createVideoLessonUpdateRequestDto;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@WithMockUser
class LessonControllerIT extends AbstractCommonIT {

    @Autowired
    private CourseService courseService;
    @Autowired
    private LessonService lessonService;

    @Test
    void getById_givenId_shouldReturn200() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var lesson = createVideoLesson(course);
        var id = lesson.getId();

        // when, then
        var request = buildGetByIdRequest(id);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        courseService.deleteById(courseId);
        lessonService.deleteById(id);
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
    void updateById_givenVideoLessonRequestDto_shouldSuccessfullyUpdateAndReturn200() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var lesson = createVideoLesson(course);
        var id = lesson.getId();

        var requestDto = createVideoLessonUpdateRequestDto(id, courseId);
        var content = objectMapper.writeValueAsString(requestDto);

        // when, then
        var request = buildUpdateByIdRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(id.toString()),
                        jsonPath("$.url").value(lesson.getUrl()),
                        jsonPath("$.platform").value(lesson.getPlatform())
                );

        courseService.deleteById(courseId);
        lessonService.deleteById(id);
    }

    @Test
    void updateById_givenClassroomLessonRequestDto_shouldSuccessfullyUpdateAndReturn200() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var lesson = createClassroomLesson(course);
        var id = lesson.getId();

        var requestDto = createClassroomLessonUpdateRequestDto(id, courseId);
        var content = objectMapper.writeValueAsString(requestDto);

        // when, then
        var request = buildUpdateByIdRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(id.toString()),
                        jsonPath("$.location").value(lesson.getLocation()),
                        jsonPath("$.capacity").value(lesson.getCapacity())
                );

        courseService.deleteById(courseId);
        lessonService.deleteById(id);
    }

    @Test
    void updateById_givenInvalidBody_shouldReturn400() throws Exception {
        // given
        var id = UUID.randomUUID();

        var content = "{\"id\": \"%s\"}".formatted(id);

        // when, then
        var request = buildUpdateByIdRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateById_givenLessonId_shouldReturn404() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var id = UUID.randomUUID();

        var requestDto = createVideoLessonRequestDto(courseId);
        var content = objectMapper.writeValueAsString(requestDto);

        // when, then
        var request = buildUpdateByIdRequest(id, content);
        mockMvc.perform(request)
                .andExpect(status().isNotFound());

        courseService.deleteById(courseId);
    }

    @Test
    void deleteById_givenId_shouldSuccessfullyDeleteAndReturn204() throws Exception {
        // given
        var course = createCourse();
        var courseId = course.getId();
        var lesson = createVideoLesson(course);
        var id = lesson.getId();

        // when, then
        var deleteRequest = buildDeleteByIdRequest(id);
        mockMvc.perform(deleteRequest)
                .andExpect(status().isNoContent());

        assertThrows(EntityNotFoundException.class, () -> lessonService.getById(id));

        courseService.deleteById(courseId);
    }

    private ClassroomLesson createClassroomLesson(Course course) {
        var lesson = buildClassroomLesson(course);
        return (ClassroomLesson) lessonService.create(course.getId(), lesson);
    }

    private VideoLesson createVideoLesson(Course course) {
        var lesson = buildVideoLesson(course);
        return (VideoLesson) lessonService.create(course.getId(), lesson);
    }

    private Course createCourse() {
        var course = buildCourse();
        return courseService.create(course);
    }
}
