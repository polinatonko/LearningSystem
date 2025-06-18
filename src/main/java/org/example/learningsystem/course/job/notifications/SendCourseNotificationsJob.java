package org.example.learningsystem.course.job.notifications;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.service.TenantService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendCourseNotificationsJob {

    private final CourseNotificationsService courseNotificationsService;
    private final CourseReminderProperties reminderProperties;
    private final TenantService tenantService;

    @Scheduled(cron = "#{courseReminderProperties.cron}")
    public void execute() {
        if (!reminderProperties.getEnabled()) {
            return;
        }

        tenantService.executeForAllTenants(this::sendNotifications);
    }

    public void sendNotifications() {
        int days = reminderProperties.getDaysBefore();
        courseNotificationsService.send(days);
    }
}
