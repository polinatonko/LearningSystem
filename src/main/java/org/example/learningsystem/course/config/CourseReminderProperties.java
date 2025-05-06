package org.example.learningsystem.course.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("course.reminder")
@Component
@Validated
@Getter
@Setter
public class CourseReminderProperties {

    @NotNull
    private String cron;
    @NotNull
    @Min(0)
    @Max(30)
    private Integer daysBefore;
    private Boolean enabled = true;
}