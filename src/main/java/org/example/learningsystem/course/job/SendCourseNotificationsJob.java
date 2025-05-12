package org.example.learningsystem.course.job;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.service.CourseNotificationService;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.course.config.CourseReminderProperties;
import org.example.learningsystem.course.service.EmailServerPropertiesResolver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendCourseNotificationsJob {

    private final CourseService courseService;
    private final CourseNotificationService courseNotificationService;
    private final EmailServerPropertiesResolver emailServerPropertiesResolver;
    private final CourseReminderProperties reminderProperties;

    @Scheduled(cron = "#{courseReminderProperties.cron}")
    public void execute() {
        if (!reminderProperties.getEnabled()) {
            return;
        }

        var emailProperties = emailServerPropertiesResolver.getEmailServerProperties();
        var upcomingCourses = courseService.getUpcoming(reminderProperties.getDaysBefore());

        for (var course : upcomingCourses) {
            courseNotificationService.sendCourseNotifications(course, emailProperties);
        }
    }

}
