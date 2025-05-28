package org.example.learningsystem.util;

import org.example.learningsystem.course.dto.CourseRequestDto;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseSettings;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CourseTestUtils {

    public static final String TITLE = "Course title";
    public static final String DESCRIPTION = "Course description";
    public static final BigDecimal PRICE = BigDecimal.valueOf(100);
    public static final BigDecimal ENOUGH_COINS = PRICE.add(BigDecimal.ONE);
    public static final BigDecimal NOT_ENOUGH_COINS = PRICE.subtract(BigDecimal.ONE);
    public static final BigDecimal COINS_PAID = BigDecimal.ZERO;
    public static final LocalDateTime CREATED = LocalDateTime.now();
    public static final LocalDateTime LAST_CHANGED = LocalDateTime.now();
    public static final LocalDateTime START_DATE = LocalDateTime.of(2025, 6, 1, 10, 0, 0);
    public static final LocalDateTime END_DATE = LocalDateTime.of(2025, 7, 1, 10, 0, 0);
    public static final boolean IS_PUBLIC = true;

    public static Course createCourse() {
        var settings = createCourseSettings();
        var course = Course.builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .price(PRICE)
                .coinsPaid(COINS_PAID)
                .settings(settings)
                .build();
        settings.setCourse(course);
        return course;
    }

    public static Course createSavedCourse() {
        var settings = createSavedCourseSettings();
        var course = Course.builder()
                .id(settings.getId())
                .title(TITLE)
                .description(DESCRIPTION)
                .price(PRICE)
                .coinsPaid(COINS_PAID)
                .created(CREATED)
                .lastChanged(LAST_CHANGED)
                .settings(settings)
                .build();
        settings.setCourse(course);
        return course;
    }

    public static CourseSettings createCourseSettings() {
        return CourseSettings.builder()
                .startDate(START_DATE)
                .endDate(END_DATE)
                .isPublic(IS_PUBLIC)
                .build();
    }

    public static CourseSettings createSavedCourseSettings() {
        return CourseSettings.builder()
                .id(UUID.randomUUID())
                .startDate(START_DATE)
                .endDate(END_DATE)
                .isPublic(IS_PUBLIC)
                .created(CREATED)
                .lastChanged(LAST_CHANGED)
                .build();
    }

    public static CourseRequestDto createCreateCourseRequestDto() {
        return new CourseRequestDto(
                null,
                TITLE,
                DESCRIPTION,
                PRICE,
                COINS_PAID,
                START_DATE,
                END_DATE,
                IS_PUBLIC
        );
    }

}
