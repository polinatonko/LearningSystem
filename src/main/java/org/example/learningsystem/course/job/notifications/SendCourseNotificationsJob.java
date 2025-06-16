package org.example.learningsystem.course.job.notifications;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.db.datasource.MultiTenantDataSource;
import org.example.learningsystem.course.service.CourseService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendCourseNotificationsJob {

    private final CourseService courseService;
    private final CourseNotificationsService courseNotificationsService;
    private final CourseReminderProperties reminderProperties;
    private final MultiTenantDataSource multiTenantDataSource;

    @Scheduled(cron = "#{courseReminderProperties.cron}")
    public void execute() {
        if (!reminderProperties.getEnabled()) {
            return;
        }

        try {
            executeForTenant();
            executeForAllTenants();
        } finally {
            TenantContext.clear();
        }
    }

    private void executeForTenant() {
        var upcomingCourses = courseService.getUpcoming(reminderProperties.getDaysBefore());
        courseNotificationsService.send(upcomingCourses);
    }

    private void executeForAllTenants() {
        var dataSources = multiTenantDataSource.getResolvedDataSources();
        for (var tenant : dataSources.keySet()) {
            TenantContext.setTenant((TenantInfo) tenant);
            executeForTenant();
        }
    }
}
