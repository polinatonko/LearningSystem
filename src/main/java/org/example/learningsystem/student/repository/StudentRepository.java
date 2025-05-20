package org.example.learningsystem.student.repository;

import org.example.learningsystem.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Student s WHERE s.id = ?1")
    Optional<Student> findByIdForUpdate(UUID id);

    List<Student> findAllByEnrollmentsCourseId(UUID courseId);
}
