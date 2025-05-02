package org.example.learningsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "mail")
    public SendingEmailProperties mailProperties() {
        return new SendingEmailProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "notification.reminder")
    public CourseReminderProperties courseReminderProperties() {
        return new CourseReminderProperties();
    }
}
