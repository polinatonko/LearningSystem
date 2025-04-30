package org.example.learningsystem.rest;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Student;
import org.example.learningsystem.dto.student.StudentRequest;
import org.example.learningsystem.dto.student.StudentResponse;
import org.example.learningsystem.mapper.StudentMapper;
import org.example.learningsystem.service.student.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse create(@RequestBody StudentRequest studentRequest) {
        var student = studentService.create(studentMapper.requestToStudent(studentRequest));
        return toResponse(student);
    }

    @GetMapping("/{id}")
    public StudentResponse get(@PathVariable UUID id) {
        var student = studentService.getById(id);
        return toResponse(student);
    }

    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable UUID id, @RequestBody StudentRequest studentRequest) {
        var student = studentMapper.requestToStudent(studentRequest);
        student.setId(id);
        var updatedStudent = studentService.update(student);
        return toResponse(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        studentService.delete(id);
    }

    private StudentResponse toResponse(Student student) {
        return studentMapper.studentToResponse(student);
    }
}
