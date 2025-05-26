package org.example.learningsystem.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.dto.CourseRequestDto;
import org.example.learningsystem.course.dto.CourseResponseDto;
import org.example.learningsystem.lesson.dto.LessonRequestDto;
import org.example.learningsystem.lesson.dto.LessonResponseDto;
import org.example.learningsystem.student.dto.StudentResponseDto;
import org.example.learningsystem.course.mapper.CourseMapper;
import org.example.learningsystem.lesson.mapper.LessonMapper;
import org.example.learningsystem.student.mapper.StudentMapper;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.course.service.CourseEnrollmentService;
import org.example.learningsystem.lesson.service.LessonService;
import org.example.learningsystem.student.service.StudentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Course Controller")
public class CourseController {

    private final CourseEnrollmentService courseEnrollmentService;
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course was created"),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public CourseResponseDto create(@RequestBody @Valid CourseRequestDto courseRequestDto) {
        var course = courseMapper.toEntity(courseRequestDto);
        var savedCourse = courseService.create(course);
        return courseMapper.toDto(savedCourse);
    }

    @PostMapping("/{id}/students/{studentId}")
    @Operation(summary = "Enroll student in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was enrolled"),
            @ApiResponse(responseCode = "400", description = "Invalid values of path variables")
    })
    public void enrollStudent(@PathVariable UUID id, @PathVariable UUID studentId) {
        courseEnrollmentService.enrollStudent(id, studentId);
    }

    @PostMapping("/{id}/lessons")
    @ResponseStatus(CREATED)
    @Operation(summary = "Create lesson in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lesson was created"),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public LessonResponseDto createLesson(@PathVariable UUID id, @RequestBody @Valid LessonRequestDto lessonRequestDto) {
        var lesson = lessonMapper.toEntity(lessonRequestDto);
        var createdLesson = lessonService.create(id, lesson);
        return lessonMapper.toDto(createdLesson);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course was retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    public CourseResponseDto getById(@PathVariable UUID id) {
        var course = courseService.getById(id);
        return courseMapper.toDto(course);
    }

    @GetMapping
    @Operation(summary = "Get all courses")
    @ApiResponse(responseCode = "200", description = "Courses were retrieved")
    public List<CourseResponseDto> getAll() {
        var courses = courseService.getAll();
        return courseMapper.toDtos(courses);
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Get all students enrolled in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students were retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    public List<StudentResponseDto> getStudents(@PathVariable UUID id) {
        var students = studentService.getAllByCourseId(id);
        return studentMapper.toDtos(students);
    }

    @GetMapping("/{id}/lessons")
    @Operation(summary = "Get all lessons in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lessons were retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    public List<LessonResponseDto> getLessons(@PathVariable UUID id) {
        var lessons = lessonService.getAllByCourseId(id);
        return lessonMapper.toDtos(lessons);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course was updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or value of path variable"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    public CourseResponseDto updateById(@PathVariable UUID id, @RequestBody @Valid CourseRequestDto courseRequestDto) {
        var course = courseService.getById(id);
        courseMapper.toEntity(courseRequestDto, course);
        var updatedCourse = courseService.update(course);
        return courseMapper.toDto(updatedCourse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete course")
    @ApiResponse(responseCode = "204", description = "Course was deleted or doesn't exist")
    public void deleteById(@PathVariable UUID id) {
        courseService.delete(id);
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
