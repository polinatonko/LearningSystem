package org.example.learningsystem.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.dto.student.StudentRequestDto;
import org.example.learningsystem.dto.student.StudentResponseDto;
import org.example.learningsystem.mapper.StudentMapper;
import org.example.learningsystem.service.student.StudentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Tag(name = "Student Controller")
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student was created"),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public StudentResponseDto create(@RequestBody @Valid StudentRequestDto studentRequestDto) {
        var student = studentService.create(studentMapper.toEntity(studentRequestDto));
        return studentMapper.toDto(student);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Student was not found")
    })
    public StudentResponseDto getById(@PathVariable UUID id) {
        var student = studentService.getById(id);
        return studentMapper.toDto(student);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or value of path variable"),
            @ApiResponse(responseCode = "404", description = "Student was not found")
    })
    public StudentResponseDto updateById(@PathVariable UUID id, @RequestBody @Valid StudentRequestDto studentRequestDto) {
        var student = studentService.getById(id);
        studentMapper.toEntity(studentRequestDto, student);
        var updatedStudent = studentService.update(student);
        return studentMapper.toDto(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete course")
    @ApiResponse(responseCode = "204", description = "Student was deleted or doesn't exist")
    public void deleteById(@PathVariable UUID id) {
        studentService.delete(id);
    }
}
