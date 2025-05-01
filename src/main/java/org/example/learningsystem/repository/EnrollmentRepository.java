package org.example.learningsystem.repository;

import org.example.learningsystem.domain.Enrollment;
import org.example.learningsystem.domain.EnrollmentId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends CrudRepository<Enrollment, EnrollmentId> {
}
