package org.example.learningsystem.course.common.builder;

import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.UUID;

import static org.example.learningsystem.common.builder.PaginatedRequestBuilder.build;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class CourseRequestBuilder {

    private static final String COURSES_URL = "/api/v1/courses";
    private static final String COURSE_URL = "/api/v1/courses/{courseId}";
    private static final String COURSE_LESSONS_URL = "/api/v1/courses/{courseId}/lessons";

    public static RequestBuilder buildCreateRequest(String content) {
        return post(COURSES_URL)
                .contentType(APPLICATION_JSON)
                .content(content);
    }

    public static RequestBuilder buildCreateLessonRequest(UUID courseId, String content) {
        return post(COURSE_LESSONS_URL, courseId)
                .contentType(APPLICATION_JSON)
                .content(content);
    }

    public static RequestBuilder buildDeleteByIdRequest(UUID id) {
        return delete(COURSE_URL, id);
    }

    public static RequestBuilder buildGetAllRequest(PageRequest pageRequest) {
        var builder = get(COURSES_URL);
        return build(builder, pageRequest);
    }

    public static RequestBuilder buildGetByIdRequest(UUID id) {
        return get(COURSE_URL, id);
    }

    public static RequestBuilder buildGetLessonsByIdRequest(UUID courseId, PageRequest pageRequest) {
        var builder = get(COURSE_LESSONS_URL, courseId);
        return build(builder, pageRequest);
    }

    public static RequestBuilder buildUpdateByIdRequest(UUID id, String content) {
        return put(COURSE_URL, id)
                .contentType(APPLICATION_JSON)
                .content(content);
    }
}
