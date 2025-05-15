package org.example.learningsystem.course.service;

import java.util.UUID;

public interface CourseEnrollmentService {

    void enrollStudent(UUID courseId, UUID studentId);

    void unenrollStudent(UUID courseId, UUID studentId);
}
