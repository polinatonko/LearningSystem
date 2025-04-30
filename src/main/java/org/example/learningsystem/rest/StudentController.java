package org.example.learningsystem.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Student Controller")
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student was created"),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public StudentResponse create(@RequestBody StudentRequest studentRequest) {
        var student = studentService.create(studentMapper.requestToStudent(studentRequest));
        return toResponse(student);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student via id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Student was not found")
    })
    public StudentResponse get(@PathVariable UUID id) {
        var student = studentService.getById(id);
        return toResponse(student);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or value of path variable"),
            @ApiResponse(responseCode = "404", description = "Student was not found")
    })
    public StudentResponse update(@PathVariable UUID id, @RequestBody StudentRequest studentRequest) {
        var student = studentMapper.requestToStudent(studentRequest);
        student.setId(id);
        var updatedStudent = studentService.update(student);
        return toResponse(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete course")
    @ApiResponse(responseCode = "204", description = "Student was deleted or doesn't exist")
    public void delete(@PathVariable UUID id) {
        studentService.delete(id);
    }

    private StudentResponse toResponse(Student student) {
        return studentMapper.studentToResponse(student);
    }
}
