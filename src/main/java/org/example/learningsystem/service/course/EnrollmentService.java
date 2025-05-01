package org.example.learningsystem.service.course;

import java.util.UUID;

public interface EnrollmentService {
    void enrollStudent(UUID courseId, UUID studentId);
    void unenrollStudent(UUID courseId, UUID studentId);
}
