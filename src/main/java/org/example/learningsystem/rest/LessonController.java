package org.example.learningsystem.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Lesson;
import org.example.learningsystem.dto.lesson.LessonRequestDto;
import org.example.learningsystem.dto.lesson.LessonResponseDto;
import org.example.learningsystem.mapper.LessonMapper;
import org.example.learningsystem.service.lesson.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@Tag(name = "Lesson Controller")
public class LessonController {
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get lesson by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson was retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Lesson was not found")
    })
    public LessonResponseDto get(@PathVariable UUID id) {
        var lesson = lessonService.getById(id);
        return lessonMapper.toDto(lesson);
    }

    @GetMapping
    @Operation(summary = "Get all lessons")
    @ApiResponse(responseCode = "200", description = "Lessons were retrieved")
    public List<LessonResponseDto> getAll() {
        var lessons = lessonService.getAll();
        return lessonMapper.toDtos(lessons);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update lesson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson was updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or value of path variable"),
            @ApiResponse(responseCode = "404", description = "Lesson was not found")
    })
    public LessonResponseDto update(@PathVariable UUID id, @RequestBody LessonRequestDto lessonRequestDto) {
        var lesson = lessonMapper.toEntity(lessonRequestDto);
        lesson.setId(id);
        var updatedLesson = lessonService.update(lesson);
        return toDto(updatedLesson);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete lesson")
    @ApiResponse(responseCode = "204", description = "Lesson was deleted or doesn't exist")
    public void delete(@PathVariable UUID id) {
        lessonService.delete(id);
    }

    private LessonResponseDto toDto(Lesson lesson) {
        return lessonMapper.toDto(lesson);
    }
}
