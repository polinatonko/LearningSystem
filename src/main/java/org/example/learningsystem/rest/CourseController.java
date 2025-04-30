package org.example.learningsystem.rest;

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
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse create(@RequestBody CourseRequest courseRequest) {
        var course = courseService.create(courseMapper.requestToCourse(courseRequest));
        return toResponse(course);
    }

    @PostMapping("/{id}/students/{studentId}")
    public void enrollStudentInCourse(@PathVariable UUID id, @PathVariable UUID studentId) {
        courseService.enrollStudent(studentId, id);
    }

    @GetMapping("/{id}")
    public CourseResponse get(@PathVariable UUID id) {
        var course = courseService.getById(id);
        return toResponse(course);
    }

    @GetMapping
    public List<CourseResponse> getAll() {
        var courses = courseService.getAll();
        return courses.stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}/students")
    public List<StudentResponse> getStudents(@PathVariable UUID id) {
        var students = studentService.getByCourseId(id);
        return students.stream()
                .map(studentMapper::studentToResponse)
                .toList();
    }

    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable UUID id, @RequestBody CourseRequest courseRequest) {
        var course = courseMapper.requestToCourse(courseRequest);
        course.setId(id);
        var updatedCourse = courseService.update(course);
        return toResponse(updatedCourse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        courseService.delete(id);
    }

    @DeleteMapping("/{id}/students/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unenrollStudentFromCourse(@PathVariable UUID id, @PathVariable UUID studentId) {
        courseService.unenrollStudent(id, studentId);
    }

    private CourseResponse toResponse(Course course) {
        return courseMapper.courseToResponse(course);
    }
}
