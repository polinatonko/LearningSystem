package org.example.learningsystem.lesson.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.lesson.common.dto.LessonRequestDto;
import org.example.learningsystem.lesson.common.dto.LessonResponseDto;
import org.example.learningsystem.lesson.common.mapper.LessonMapper;
import org.example.learningsystem.lesson.common.service.LessonService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;

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
    public LessonResponseDto getById(@PathVariable UUID id) {
        var lesson = lessonService.getById(id);
        return lessonMapper.toDto(lesson);
    }

    @GetMapping
    @Operation(summary = "Get all lessons")
    @ApiResponse(responseCode = "200", description = "Lessons were retrieved")
    public PagedModel<LessonResponseDto> getAll(@PageableDefault(size = 5, sort = "created") Pageable pageable) {
        var lessons = lessonService.getAll(pageable);
        var lessonsMapped = lessons.map(lessonMapper::toDto);
        return new PagedModel<>(lessonsMapped);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update lesson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson was updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or value of path variable"),
            @ApiResponse(responseCode = "404", description = "Lesson was not found")
    })
    public LessonResponseDto updateById(@PathVariable UUID id, @RequestBody @Valid LessonRequestDto lessonRequestDto) {
        var lesson = lessonMapper.toEntity(lessonRequestDto);
        var updatedLesson = lessonService.update(lessonRequestDto.getCourseId(), lesson);
        return lessonMapper.toDto(updatedLesson);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete lesson")
    @ApiResponse(responseCode = "204", description = "Lesson was deleted or doesn't exist")
    public void deleteById(@PathVariable UUID id) {
        lessonService.deleteById(id);
    }
}
