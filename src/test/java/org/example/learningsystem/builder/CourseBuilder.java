package org.example.learningsystem.builder;

import org.example.learningsystem.domain.Course;
import org.example.learningsystem.domain.CourseSettings;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CourseBuilder {

    private UUID id;
    private String title = "Test course";
    private String description = "Course description";
    private BigDecimal price = BigDecimal.valueOf(100);
    private BigDecimal coinsPaid = BigDecimal.ZERO;
    private CourseSettings settings = new CourseSettings();

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

    public CourseBuilder isPublic(boolean isPublic) {
        settings.setIsPublic(isPublic);
        return this;
    }

    public CourseBuilder startDate(LocalDateTime startDate) {
        settings.setStartDate(startDate);
        return this;
    }

    public CourseBuilder endDate(LocalDateTime endDate) {
        settings.setEndDate(endDate);
        return this;
    }

    public Course build() {
        var course = new Course(title, description, price, coinsPaid);
        course.setId(id);
        course.setSettings(settings);
        return course;
    }
}
