package org.example.learningsystem.util;

import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseSettings;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class CourseBuilder {

    public UUID id;
    public String title = "Course title";
    public String description = "Course description";
    public BigDecimal price = BigDecimal.valueOf(100);
    public BigDecimal coinsPaid = BigDecimal.ZERO;
    public Instant created = Instant.now();
    public Instant lastChanged = Instant.now();
    public LocalDateTime startDate = LocalDateTime.of(2025, 6, 1, 10, 0, 0);
    public LocalDateTime endDate = LocalDateTime.of(2025, 7, 1, 10, 0, 0);
    public boolean isPublic = true;

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
