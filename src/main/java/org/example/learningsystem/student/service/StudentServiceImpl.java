package org.example.learningsystem.student.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.student.model.Student;
import org.example.learningsystem.exception.logic.EntityNotFoundException;
import org.example.learningsystem.student.repository.StudentRepository;
import org.example.learningsystem.core.util.validator.EntityValidator;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "student")
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final EntityValidator<Student> studentValidator;

    @Override
    public Student create(Student student) {
        studentValidator.validateForInsert(student);
        return studentRepository.save(student);
    }

    @Override
    @Cacheable
    public Student getById(UUID id) {
        return findById(id);
    }

    @Override
    @Cacheable
    public Student getByIdForUpdate(UUID id) {
        return findByIdForUpdate(id);
    }

    @Override
    public Page<Student> getAllByCourseId(UUID courseId, Pageable pageable) {
        return studentRepository.findAllByEnrollmentsCourseId(courseId, pageable);
    }

    @Override
    public Page<Student> getAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    @CachePut(key = "#student.id")
    public Student update(Student student) {
        studentValidator.validateForUpdate(student);
        findById(student.getId());
        return studentRepository.save(student);
    }

    @Override
    @CacheEvict
    public void deleteById(UUID id) {
        studentRepository.deleteById(id);
    }

    private Student findById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Student.class.getName(), id));
    }

    private Student findByIdForUpdate(UUID id) {
        return studentRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new EntityNotFoundException(Student.class.getName(), id));
    }
}
