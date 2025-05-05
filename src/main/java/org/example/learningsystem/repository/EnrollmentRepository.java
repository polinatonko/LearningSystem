package org.example.learningsystem.repository;

import org.example.learningsystem.domain.Enrollment;
import org.example.learningsystem.domain.EnrollmentId;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EnrollmentRepository extends CrudRepository<Enrollment, EnrollmentId> {

    boolean existsByCourseIdAndStudentId(UUID courseId, UUID enrollmentId);
}
