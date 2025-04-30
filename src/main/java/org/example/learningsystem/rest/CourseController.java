package org.example.learningsystem.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Course;
import org.example.learningsystem.dto.course.CourseRequest;
import org.example.learningsystem.dto.course.CourseResponse;
import org.example.learningsystem.dto.student.StudentResponse;
import org.example.learningsystem.mapper.CourseMapper;
import org.example.learningsystem.mapper.StudentMapper;
import org.example.learningsystem.service.course.CourseService;
import org.example.learningsystem.service.student.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Course Controller")
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course was created"),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public CourseResponse create(@RequestBody CourseRequest courseRequest) {
        var course = courseService.create(courseMapper.requestToCourse(courseRequest));
        return toResponse(course);
    }

    @PostMapping("/{id}/students/{studentId}")
    @Operation(summary = "Enroll student in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was enrolled"),
            @ApiResponse(responseCode = "400", description = "Invalid values of path variables")
    })
    public void enrollStudentInCourse(@PathVariable UUID id, @PathVariable UUID studentId) {
        courseService.enrollStudent(studentId, id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course via id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course was retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    public CourseResponse get(@PathVariable UUID id) {
        var course = courseService.getById(id);
        return toResponse(course);
    }

    @GetMapping
    @Operation(summary = "Get all courses")
    @ApiResponse(responseCode = "200", description = "Courses were retrieved")
    public List<CourseResponse> getAll() {
        var courses = courseService.getAll();
        return courses.stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Get all students enrolled in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students were retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    public List<StudentResponse> getStudents(@PathVariable UUID id) {
        var students = studentService.getByCourseId(id);
        return students.stream()
                .map(studentMapper::studentToResponse)
                .toList();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course was updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or value of path variable"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    public CourseResponse update(@PathVariable UUID id, @RequestBody CourseRequest courseRequest) {
        var course = courseMapper.requestToCourse(courseRequest);
        course.setId(id);
        var updatedCourse = courseService.update(course);
        return toResponse(updatedCourse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete course")
    @ApiResponse(responseCode = "204", description = "Course was deleted or doesn't exist")
    public void delete(@PathVariable UUID id) {
        courseService.delete(id);
    }

    @DeleteMapping("/{id}/students/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Unenroll student from course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was unenrolled"),
            @ApiResponse(responseCode = "400", description = "Invalid values of path variables"),
            @ApiResponse(responseCode = "404", description = "Course or student was not found")
    })
    public void unenrollStudentFromCourse(@PathVariable UUID id, @PathVariable UUID studentId) {
        courseService.unenrollStudent(id, studentId);
    }

    private CourseResponse toResponse(Course course) {
        return courseMapper.courseToResponse(course);
    }
}
