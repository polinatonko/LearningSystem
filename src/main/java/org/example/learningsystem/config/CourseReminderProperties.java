package org.example.learningsystem.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

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