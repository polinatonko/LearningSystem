package org.example.learningsystem.course.job;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.job.model.CourseNotificationsProperties;
import org.example.learningsystem.course.job.service.CourseNotificationsService;
import org.example.learningsystem.multitenancy.service.TenantService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendCourseNotificationsJob {

    private final CourseNotificationsService courseNotificationsService;
    private final CourseNotificationsProperties reminderProperties;
    private final TenantService tenantService;

    @Scheduled(cron = "#{courseNotificationsProperties.cron}")
    public void execute() {
        if (!reminderProperties.getEnabled()) {
            return;
        }

        tenantService.executeForAll(this::sendNotifications);
    }

    public void sendNotifications() {
        int days = reminderProperties.getDaysBefore();
        courseNotificationsService.send(days);
    }
}
