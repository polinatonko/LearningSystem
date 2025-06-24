package org.example.learningsystem.course.repository;

import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.course.model.CourseEnrollmentId;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CourseEnrollmentRepository extends CrudRepository<CourseEnrollment, CourseEnrollmentId> {

    boolean existsByCourseIdAndStudentId(UUID courseId, UUID enrollmentId);
}
