package org.example.learningsystem.common.util;

import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseSettings;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class CourseBuilder {

    private UUID id;
    private String title = "Course title";
    private String description = "Course description";
    private BigDecimal price = BigDecimal.valueOf(100);
    private BigDecimal coinsPaid = BigDecimal.ZERO;
    private Instant created = Instant.now();
    private Instant lastChanged = Instant.now();
    private LocalDateTime startDate = LocalDateTime.of(2025, 6, 1, 10, 0, 0);
    private LocalDateTime endDate = LocalDateTime.of(2025, 7, 1, 10, 0, 0);
    private boolean isPublic = true;

    public CourseBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public CourseBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CourseBuilder description(String description) {
        this.description = description;
        return this;
    }

    public CourseBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CourseBuilder coinsPaid(BigDecimal coinsPaid) {
        this.coinsPaid = coinsPaid;
        return this;
    }

    public CourseBuilder startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public CourseBuilder endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public CourseBuilder isPublic(boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public Course build() {
        var settings = CourseSettings.builder()
                .id(id)
                .isPublic(isPublic)
                .startDate(startDate)
                .endDate(endDate)
                .created(created)
                .lastChanged(lastChanged)
                .build();
        var course = Course.builder()
                .id(id)
                .title(title)
                .description(description)
                .price(price)
                .coinsPaid(coinsPaid)
                .created(created)
                .lastChanged(lastChanged)
                .build();
        course.setSettings(settings);
        return course;
    }

}
