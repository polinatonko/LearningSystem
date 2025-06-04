package org.example.learningsystem.lesson.common.builder;

import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.UUID;

import static org.example.learningsystem.common.builder.PaginatedRequestBuilder.build;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class LessonRequestBuilder {

    private static final String LESSONS_URL = "/lessons";
    private static final String LESSON_URL = "/lessons/{id}";

    public static RequestBuilder buildDeleteByIdRequest(UUID id) {
        return delete(LESSON_URL, id);
    }

    public static RequestBuilder buildGetAllRequest(PageRequest pageRequest) {
        var builder = get(LESSONS_URL);
        return build(builder, pageRequest);
    }

    public static RequestBuilder buildGetByIdRequest(UUID id) {
        return get(LESSON_URL, id);
    }

    public static RequestBuilder buildUpdateByIdRequest(UUID id, String content) {
        return put(LESSON_URL, id)
                .contentType(APPLICATION_JSON)
                .content(content);
    }
}
