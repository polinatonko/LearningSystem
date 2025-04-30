package org.example.learningsystem.service.student;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Student;
import org.example.learningsystem.exception.EntityNotFoundException;
import org.example.learningsystem.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getById(UUID id) {
        return findByIdOrThrowException(id);
    }

    @Override
    public List<Student> getByCourseId(UUID courseId) {
        return studentRepository.findByCoursesId(courseId);
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student update(Student student) {
        findByIdOrThrowException(student.getId());
        return studentRepository.save(student);
    }

    @Override
    public void delete(UUID id) {
        studentRepository.deleteById(id);
    }

    private Student findByIdOrThrowException(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Student.class.getName(), id));
    }
}
