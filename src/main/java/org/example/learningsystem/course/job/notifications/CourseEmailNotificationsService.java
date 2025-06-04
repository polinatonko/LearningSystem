package org.example.learningsystem.course.job.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.email.config.EmailServerProperties;
import org.example.learningsystem.email.service.EmailServerPropertiesResolver;
import org.example.learningsystem.email.service.EmailService;
import org.example.learningsystem.student.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseEmailNotificationsService implements CourseNotificationsService {

    private final CourseNotificationBuilder courseNotificationBuilder;
    private final EmailServerPropertiesResolver emailServerPropertiesResolver;
    private final EmailService emailService;

    @Override
    public void send(List<Course> courses) {
        var emailServerProperties = emailServerPropertiesResolver.resolve();
        courses.forEach(course -> sendNotifications(course, emailServerProperties));
    }

    private void sendNotifications(Course course, EmailServerProperties emailServerProperties) {
        course.getEnrollments()
                .stream()
                .map(CourseEnrollment::getStudent)
                .forEach(student ->
                        sendNotification(course, student, emailServerProperties));
    }

    private void sendNotification(Course course, Student student, EmailServerProperties emailServerProperties) {
        var notification = courseNotificationBuilder.build(course, student);
        log.info("Prepared email for {}: {}", student.getEmail(), notification.message());
        emailService.send(student.getEmail(), notification.subject(), notification.message(), emailServerProperties);
    }
}
