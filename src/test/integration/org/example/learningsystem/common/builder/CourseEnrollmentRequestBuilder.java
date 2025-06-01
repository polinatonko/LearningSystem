package org.example.learningsystem.common.builder;

import org.springframework.test.web.servlet.RequestBuilder;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CourseEnrollmentRequestBuilder {

    private static final String ENROLLMENT_URL = "/courses/{id}/students/{studentId}";

    public static RequestBuilder buildEnrollRequest(UUID courseId, UUID studentId) {
        return post(ENROLLMENT_URL, courseId, studentId);
    }

    public static RequestBuilder buildUnenrollRequest(UUID courseId, UUID studentId) {
        return delete(ENROLLMENT_URL, courseId, studentId);
    }

}
