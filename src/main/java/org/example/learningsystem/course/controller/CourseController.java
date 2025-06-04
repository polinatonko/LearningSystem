package org.example.learningsystem.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.dto.CourseRequestDto;
import org.example.learningsystem.course.dto.CourseResponseDto;
import org.example.learningsystem.lesson.common.dto.LessonRequestDto;
import org.example.learningsystem.lesson.common.dto.LessonResponseDto;
import org.example.learningsystem.student.dto.StudentResponseDto;
import org.example.learningsystem.course.mapper.CourseMapper;
import org.example.learningsystem.lesson.common.mapper.LessonMapper;
import org.example.learningsystem.student.mapper.StudentMapper;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.lesson.common.service.LessonService;
import org.example.learningsystem.student.service.StudentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Course Controller")
public class CourseController {

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
    public PagedModel<CourseResponseDto> getAll(@PageableDefault(size = 5, sort = "created") Pageable pageable) {
        var courses = courseService.getAll(pageable);
        var mappedCourses = courses.map(courseMapper::toDto);
        return new PagedModel<>(mappedCourses);
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Get all students enrolled in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students were retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    public PagedModel<StudentResponseDto> getStudentsByCourseId(
            @PathVariable UUID id,
            @PageableDefault(size = 5, sort = "created") Pageable pageable) {
        var students = studentService.getAllByCourseId(id, pageable);
        var mappedStudents = students.map(studentMapper::toDto);
        return new PagedModel<>(mappedStudents);
    }

    @GetMapping("/{id}/lessons")
    @Operation(summary = "Get all lessons in course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lessons were retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    public PagedModel<LessonResponseDto> getLessonsByCourseId(
            @PathVariable UUID id,
            @PageableDefault(size = 5, sort = "created") Pageable pageable) {
        var lessons = lessonService.getAllByCourseId(id, pageable);
        var mappedLessons = lessons.map(lessonMapper::toDto);
        return new PagedModel<>(mappedLessons);
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
        courseService.deleteById(id);
    }

}
