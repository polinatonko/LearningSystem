package org.example.learningsystem.course.job;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.service.CourseNotificationService;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.course.config.CourseReminderProperties;
import org.example.learningsystem.email.service.EmailServerPropertiesResolver;
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

        var emailProperties = emailServerPropertiesResolver.resolve();
        var upcomingCourses = courseService.getUpcoming(reminderProperties.getDaysBefore());

        upcomingCourses.forEach(course ->
                courseNotificationService.sendCourseNotifications(course, emailProperties));
    }

}
