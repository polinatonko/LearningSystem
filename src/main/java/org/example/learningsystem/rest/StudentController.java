package org.example.learningsystem.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Student;
import org.example.learningsystem.dto.student.StudentRequestDto;
import org.example.learningsystem.dto.student.StudentResponseDto;
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
    private final StudentService service;
    private final StudentMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student was created"),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public StudentResponseDto create(@RequestBody @Valid StudentRequestDto studentRequestDto) {
        var student = service.create(mapper.toEntity(studentRequestDto));
        return toResponse(student);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student via id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Student was not found")
    })
    public StudentResponseDto get(@PathVariable UUID id) {
        var student = service.getById(id);
        return toResponse(student);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or value of path variable"),
            @ApiResponse(responseCode = "404", description = "Student was not found")
    })
    public StudentResponseDto update(@PathVariable UUID id, @RequestBody @Valid StudentRequestDto studentRequestDto) {
        var student = mapper.toEntity(studentRequestDto);
        student.setId(id);
        var updatedStudent = service.update(student);
        return toResponse(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete course")
    @ApiResponse(responseCode = "204", description = "Student was deleted or doesn't exist")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    private StudentResponseDto toResponse(Student student) {
        return mapper.toDto(student);
    }
}
