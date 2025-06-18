package org.example.learningsystem.course.job.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.course.service.CourseService;
import org.example.learningsystem.email.config.EmailServerProperties;
import org.example.learningsystem.email.service.EmailServerPropertiesResolver;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseEmailNotificationsService implements CourseNotificationsService {

    private final CourseEmailNotificationsSender courseEmailNotificationsSender;
    private final CourseService courseService;
    private final EmailServerPropertiesResolver emailServerPropertiesResolver;
    
    @Override
    public void send(int daysBefore) {
        var emailServerProperties = emailServerPropertiesResolver.resolve();
        log.info("SMTP server properties: {} {} {}",
                emailServerProperties.getHost(),
                emailServerProperties.getPort(),
                emailServerProperties.getFrom());

        var upcomingCourses = courseService.getUpcoming(daysBefore);
        upcomingCourses.forEach(course ->
                sendNotifications(course, emailServerProperties));
    }

    public void sendNotifications(Course course, EmailServerProperties emailServerProperties) {
        course.getEnrollments()
                .stream()
                .map(CourseEnrollment::getStudent)
                .forEach(student ->
                        courseEmailNotificationsSender.send(course, student, emailServerProperties));
    }
}
