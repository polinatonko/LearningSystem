package org.example.learningsystem.student.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.student.model.Student;
import org.example.learningsystem.exception.logic.EntityNotFoundException;
import org.example.learningsystem.student.repository.StudentRepository;
import org.example.learningsystem.util.validator.EntityValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final EntityValidator<Student> studentValidator;

    @Override
    public Student create(Student student) {
        studentValidator.validateForInsert(student);
        return studentRepository.save(student);
    }

    @Override
    public Student getById(UUID id) {
        return findById(id);
    }

    @Override
    public List<Student> getAllByCourseId(UUID courseId) {
        return studentRepository.findAllByEnrollmentsCourseId(courseId);
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student update(Student student) {
        studentValidator.validateForUpdate(student);
        findById(student.getId());
        return studentRepository.save(student);
    }

    @Override
    public void delete(UUID id) {
        studentRepository.deleteById(id);
    }

    private Student findById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Student.class.getName(), id));
    }
}
