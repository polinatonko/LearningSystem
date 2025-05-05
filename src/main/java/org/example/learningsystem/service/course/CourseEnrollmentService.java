package org.example.learningsystem.service.course;

import java.util.UUID;

public interface CourseEnrollmentService {

    void enrollStudent(UUID courseId, UUID studentId);

    void unenrollStudent(UUID courseId, UUID studentId);
}
