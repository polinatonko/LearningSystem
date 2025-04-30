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
    private final StudentRepository repository;

    @Override
    public Student create(Student student) {
        return repository.save(student);
    }

    @Override
    public Student getById(UUID id) {
        return findById(id);
    }

    @Override
    public List<Student> getByCourseId(UUID courseId) {
        return repository.findByCoursesId(courseId);
    }

    @Override
    public List<Student> getAll() {
        return repository.findAll();
    }

    @Override
    public Student update(Student student) {
        findById(student.getId());
        return repository.save(student);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private Student findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Student.class.getName(), id));
    }
}
