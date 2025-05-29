package org.example.learningsystem.util;

import org.example.learningsystem.course.dto.CourseRequestDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CourseRequestDtoUtils {

    private static final String TITLE = "Course title";
    private static final String DESCRIPTION = "Course description";
    private static final BigDecimal PRICE = BigDecimal.valueOf(100);
    private static final BigDecimal COINS_PAID = BigDecimal.ZERO;
    private static final LocalDateTime START_DATE = LocalDateTime.of(2025, 6, 1, 10, 0, 0);
    private static final LocalDateTime END_DATE = LocalDateTime.of(2025, 7, 1, 10, 0, 0);
    private static final boolean IS_PUBLIC = true;

    public static CourseRequestDto createCreateCourseRequestDto() {
        return new CourseRequestDto(null, TITLE, DESCRIPTION, PRICE, COINS_PAID, START_DATE, END_DATE, IS_PUBLIC);
    }

    public static CourseRequestDto createUpdateCourseRequestDto(UUID id) {
        return new CourseRequestDto(id, TITLE, DESCRIPTION, PRICE, COINS_PAID, START_DATE, END_DATE, IS_PUBLIC);
    }

}
