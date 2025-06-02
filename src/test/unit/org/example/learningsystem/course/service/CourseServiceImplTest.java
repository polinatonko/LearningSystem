package org.example.learningsystem.course.service;

import org.example.learningsystem.course.builder.CourseBuilder;
import org.example.learningsystem.core.util.validator.EntityValidator;
import org.example.learningsystem.course.exception.InvalidCourseDurationException;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.repository.CourseRepository;
import org.example.learningsystem.core.exception.model.EntityNotFoundException;
import org.example.learningsystem.core.exception.model.validation.IllegalNullValueException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    public CourseRepository courseRepository;
    @Mock
    public EntityValidator<Course> courseValidator;
    @InjectMocks
    public CourseServiceImpl courseService;

    @Test
    void create_givenCourse_shouldSuccessfullyCreateCourse() {
        // given
        var course = createCourse();
        var savedCourse = createSavedCourse();
        var savedSettings = savedCourse.getSettings();

        when(courseRepository.save(course))
                .thenReturn(savedCourse);

        // when
        var result = courseService.create(course);
        var resultSettings = result.getSettings();

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(savedCourse.getId(), result.getId()),
                () -> assertEquals(savedCourse.getTitle(), result.getTitle()),
                () -> assertEquals(savedCourse.getDescription(), result.getDescription()),
                () -> assertEquals(savedCourse.getPrice(), result.getPrice()),
                () -> assertEquals(savedCourse.getCoinsPaid(), result.getCoinsPaid())
        );
        assertAll(
                () -> assertNotNull(result.getCreated()),
                () -> assertNotNull(result.getLastChanged()),
                () -> assertNull(result.getCreatedBy()),
                () -> assertNull(result.getLastChangedBy())
        );
        assertAll(
                () -> assertEquals(savedSettings.getIsPublic(), resultSettings.getIsPublic()),
                () -> assertEquals(savedSettings.getStartDate(), resultSettings.getStartDate()),
                () -> assertEquals(savedSettings.getEndDate(), resultSettings.getEndDate())
        );
    }

    @Test
    void create_givenCourse_shouldThrowInvalidCourseDurationException() {
        // given
        var course = createCourse();
        setInvalidDuration(course);

        doThrow(InvalidCourseDurationException.class)
                .when(courseValidator)
                .validateForInsert(course);

        // when, then
        assertThrows(InvalidCourseDurationException.class, () -> courseService.create(course));
    }

    @Test
    void getById_givenId_shouldSuccessfullyReturnCourse() {
        // given
        var savedCourse = createSavedCourse();
        var id = savedCourse.getId();

        when(courseRepository.findById(id))
                .thenReturn(Optional.of(savedCourse));

        // when
        var result = courseService.getById(id);

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(id, result.getId()));
    }

    @Test
    void getById_givenId_shouldThrowEntityNotFoundException() {
        // given
        var savedCourse = createSavedCourse();
        var id = savedCourse.getId();

        when(courseRepository.findById(id))
                .thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class, () -> courseService.getById(id));
    }

    @Test
    void getAll_givenPageable_shouldSuccessfullyReturnPage() {
        // given
        var course = createCourse();
        var pageNumber = 0;
        var pageSize = 1;
        var pageable = PageRequest.of(pageNumber, pageSize);
        var courses = List.of(course);

        when(courseRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(courses, pageable, courses.size()));

        // when
        var result = courseService.getAll(pageable);
        var resultContent = result.getContent();

        // then
        assertAll(
                () -> assertNotNull(resultContent),
                () -> assertEquals(1, resultContent.size()),
                () -> assertEquals(courses.size(), result.getTotalElements())
        );
    }

    @Test
    void update_givenCourse_shouldSuccessfullyUpdateCourse() {
        // given
        var savedCourse = createSavedCourse();
        var savedSettings = savedCourse.getSettings();

        when(courseRepository.findById(any()))
                .thenReturn(Optional.of(savedCourse));
        when(courseRepository.save(savedCourse))
                .thenReturn(savedCourse);

        // when
        var result = courseService.update(savedCourse);
        var resultSettings = result.getSettings();

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(savedCourse.getId(), result.getId()),
                () -> assertEquals(savedCourse.getTitle(), result.getTitle()),
                () -> assertEquals(savedCourse.getDescription(), result.getDescription()),
                () -> assertEquals(savedCourse.getPrice(), result.getPrice()),
                () -> assertEquals(savedCourse.getCoinsPaid(), result.getCoinsPaid())
        );
        assertAll(
                () -> assertNotNull(result.getCreated()),
                () -> assertNotNull(result.getLastChanged()),
                () -> assertNull(result.getCreatedBy()),
                () -> assertNull(result.getLastChangedBy())
        );
        assertAll(
                () -> assertEquals(savedSettings.getIsPublic(), resultSettings.getIsPublic()),
                () -> assertEquals(savedSettings.getStartDate(), resultSettings.getStartDate()),
                () -> assertEquals(savedSettings.getEndDate(), resultSettings.getEndDate())
        );
    }

    @Test
    void update_givenCourse_shouldThrowInvalidCourseDurationException() {
        // given
        var course = createCourse();
        var savedCourse = createSavedCourse();
        setInvalidDuration(course);

        when(courseRepository.findById(any()))
                .thenReturn(Optional.of(savedCourse));
        doThrow(InvalidCourseDurationException.class)
                .when(courseValidator)
                .validateForUpdate(course);

        // when, then
        assertThrows(InvalidCourseDurationException.class, () -> courseService.update(course));
    }

    @Test
    void update_givenCourse_shouldThrowIllegalNullValueException() {
        // given
        var course = createCourse();
        var savedCourse = createSavedCourse();
        course.setCoinsPaid(null);

        when(courseRepository.findById(any()))
                .thenReturn(Optional.of(savedCourse));
        doThrow(IllegalNullValueException.class)
                .when(courseValidator)
                .validateForUpdate(course);

        // when, then
        assertThrows(IllegalNullValueException.class, () -> courseService.update(course));
    }

    @Test
    void deleteById_givenId_shouldSuccessfullyDeleteCourse() {
        // given
        var savedCourse = createSavedCourse();
        var id = savedCourse.getId();

        // when
        courseService.deleteById(id);

        // then
        verify(courseRepository, times(1))
                .deleteById(id);
    }

    @Test
    void getUpcoming_givenDays_shouldSuccessfullyReturnUpcomingCourses() {
        // given
        var course = createCourse();
        int days = 1;
        var courses = List.of(course);

        when(courseRepository.findAllBySettingsStartDateBetween(any(), any()))
                .thenReturn(courses);

        // when
        var result = courseService.getUpcoming(days);

        // then
        assertEquals(1, result.size());
        verify(courseRepository, times(1))
                .findAllBySettingsStartDateBetween(any(), any());
    }

    private Course createCourse() {
        return new CourseBuilder().build();
    }

    private Course createSavedCourse() {
        return new CourseBuilder()
                .id(UUID.randomUUID())
                .build();
    }

    private void setInvalidDuration(Course course) {
        var settings = course.getSettings();
        settings.setStartDate(LocalDateTime.of(2025, 1, 1, 10, 0, 0));
        settings.setEndDate(LocalDateTime.of(2024, 1, 1, 10, 0, 0));
    }

}
