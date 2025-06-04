package org.example.learningsystem.course.job.notifications;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.service.CourseService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendCourseNotificationsJob {

    private final CourseService courseService;
    private final CourseNotificationsService courseNotificationsService;
    private final CourseReminderProperties reminderProperties;

    @Scheduled(cron = "#{courseReminderProperties.cron}")
    public void execute() {
        if (!reminderProperties.getEnabled()) {
            return;
        }

        var upcomingCourses = courseService.getUpcoming(reminderProperties.getDaysBefore());
        courseNotificationsService.send(upcomingCourses);
    }
}
