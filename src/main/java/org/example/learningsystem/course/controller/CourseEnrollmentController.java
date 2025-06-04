package org.example.learningsystem.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.service.CourseEnrollmentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Course Enrollment Controller")
public class CourseEnrollmentController {

    private final CourseEnrollmentService courseEnrollmentService;

    @PostMapping("/{id}/students/{studentId}")
    @Operation(summary = "Enroll student in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was enrolled"),
            @ApiResponse(responseCode = "400", description = "Invalid values of path variables")
    })
    public void enrollStudent(@PathVariable UUID id, @PathVariable UUID studentId) {
        courseEnrollmentService.enrollStudent(id, studentId);
    }

    @DeleteMapping("/{id}/students/{studentId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Unenroll student from course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was unenrolled"),
            @ApiResponse(responseCode = "400", description = "Invalid values of path variables"),
            @ApiResponse(responseCode = "404", description = "Course or student was not found")
    })
    public void unenrollStudent(@PathVariable UUID id, @PathVariable UUID studentId) {
        courseEnrollmentService.unenrollStudent(id, studentId);
    }
}
