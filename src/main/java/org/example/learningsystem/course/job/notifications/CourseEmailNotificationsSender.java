package org.example.learningsystem.course.job.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.email.config.EmailServerProperties;
import org.example.learningsystem.email.service.EmailService;
import org.example.learningsystem.student.model.Student;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseEmailNotificationsSender {

    private final CourseNotificationBuilder courseNotificationBuilder;
    private final EmailService emailService;

    @Async("courseRemindersTaskExecutor")
    public void send(Course course, Student student, EmailServerProperties emailServerProperties) {
        var notification = courseNotificationBuilder.build(course, student);
        log.info("Prepared email for {}: {}", student.getEmail(), notification.message());
        emailService.send(student.getEmail(), notification.subject(), notification.message(), emailServerProperties);
    }
}
