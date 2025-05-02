package org.example.learningsystem.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Course;
import org.example.learningsystem.dto.course.CourseRequestDto;
import org.example.learningsystem.dto.course.CourseResponseDto;
import org.example.learningsystem.dto.lesson.LessonRequestDto;
import org.example.learningsystem.dto.lesson.LessonResponseDto;
import org.example.learningsystem.dto.student.StudentResponseDto;
import org.example.learningsystem.mapper.CourseMapper;
import org.example.learningsystem.mapper.LessonMapper;
import org.example.learningsystem.mapper.StudentMapper;
import org.example.learningsystem.service.course.CourseService;
import org.example.learningsystem.service.course.EnrollmentService;
import org.example.learningsystem.service.lesson.LessonService;
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
    private final CourseService service;
    private final CourseMapper mapper;
    private final EnrollmentService enrollmentService;
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course was created"),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public CourseResponseDto create(@RequestBody @Valid CourseRequestDto courseRequestDto) {
        var course = mapper.toEntity(courseRequestDto);
        var savedCourse = service.create(course);
        return toResponse(savedCourse);
    }

    @PostMapping("/{id}/students/{studentId}")
    @Operation(summary = "Enroll student in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student was enrolled"),
            @ApiResponse(responseCode = "400", description = "Invalid values of path variables")
    })
    public void enrollStudentInCourse(@PathVariable UUID id, @PathVariable UUID studentId) {
        enrollmentService.enrollStudent(id, studentId);
    }

    @PostMapping("/{id}/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create lesson in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lesson was created"),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public LessonResponseDto createLesson(@PathVariable UUID id, @RequestBody @Valid LessonRequestDto lessonRequestDto) {
        var lesson = lessonMapper.toEntity(lessonRequestDto);
        lesson.getCourse().setId(id);
        var createdLesson = lessonService.create(lesson);
        return lessonMapper.toDto(createdLesson);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course was retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    public CourseResponseDto get(@PathVariable UUID id) {
        var course = service.getById(id);
        return toResponse(course);
    }

    @GetMapping
    @Operation(summary = "Get all courses")
    @ApiResponse(responseCode = "200", description = "Courses were retrieved")
    public List<CourseResponseDto> getAll() {
        var courses = service.getAll();
        return mapper.toDtos(courses);
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
    public CourseResponseDto update(@PathVariable UUID id, @RequestBody @Valid CourseRequestDto courseRequestDto) {
        var course = service.getById(id);
        mapper.toEntity(courseRequestDto, course);
        var updatedCourse = service.update(course);
        return toResponse(updatedCourse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete course")
    @ApiResponse(responseCode = "204", description = "Course was deleted or doesn't exist")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
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
        enrollmentService.unenrollStudent(id, studentId);
    }

    private CourseResponseDto toResponse(Course course) {
        return mapper.toDto(course);
    }
}
