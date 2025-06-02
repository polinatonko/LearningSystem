package org.example.learningsystem.student.builder;

import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.UUID;

import static org.example.learningsystem.common.builder.PaginatedRequestBuilder.build;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class StudentRequestBuilder {

    private static final String STUDENTS_URL = "/students";
    private static final String STUDENT_URL = "/students/{id}";

    public static RequestBuilder buildCreateRequest(String content) {
        return post(STUDENTS_URL)
                .contentType(APPLICATION_JSON)
                .content(content);
    }

    public static RequestBuilder buildDeleteByIdRequest(UUID id) {
        return delete(STUDENT_URL, id);
    }

    public static RequestBuilder buildGetAllRequest(PageRequest pageRequest) {
        var builder = get(STUDENTS_URL);
        return build(builder, pageRequest);
    }

    public static RequestBuilder buildGetByIdRequest(UUID id) {
        return get(STUDENT_URL, id);
    }

    public static RequestBuilder buildUpdateByIdRequest(UUID id, String content) {
        return put(STUDENT_URL, id)
                .contentType(APPLICATION_JSON)
                .content(content);
    }

}
