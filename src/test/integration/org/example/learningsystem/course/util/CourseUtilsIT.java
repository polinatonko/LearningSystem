package org.example.learningsystem.course.util;

import org.example.learningsystem.course.dto.CourseRequestDto;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseSettings;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CourseUtilsIT {

    private static final BigDecimal COINS_PAID = BigDecimal.ZERO;
    private static final String DESCRIPTION = "Course description";
    private static final LocalDateTime END_DATE = LocalDateTime.of(2025, 7, 1, 10, 0, 0);
    private static final boolean IS_PUBLIC = true;
    private static final BigDecimal PRICE = BigDecimal.valueOf(100);
    private static final LocalDateTime START_DATE = LocalDateTime.of(2025, 6, 1, 10, 0, 0);
    private static final String TITLE = "Course title";

    public static Course buildCourse() {
        var settings = CourseSettings.builder()
                .isPublic(IS_PUBLIC)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();
        var course = Course.builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .price(PRICE)
                .coinsPaid(COINS_PAID)
                .build();
        course.setSettings(settings);
        return course;
    }

    public static CourseRequestDto buildCreateCourseRequestDto() {
        return new CourseRequestDto(null, TITLE, DESCRIPTION, PRICE, COINS_PAID, START_DATE, END_DATE, IS_PUBLIC);
    }

    public static CourseRequestDto buildUpdateCourseRequestDto(UUID id) {
        return new CourseRequestDto(id, TITLE, DESCRIPTION, PRICE, COINS_PAID, START_DATE, END_DATE, IS_PUBLIC);
    }

}
