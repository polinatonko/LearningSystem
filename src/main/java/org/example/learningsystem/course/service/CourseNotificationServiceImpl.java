package org.example.learningsystem.course.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.email.config.EmailServerProperties;
import org.example.learningsystem.email.service.EmailService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.nonNull;
import static org.example.learningsystem.core.util.format.DataFormatUtils.EMAIL_DATE_TIME_FORMAT;

@Service
@RequiredArgsConstructor
public class CourseNotificationServiceImpl implements CourseNotificationService {

    private static final String MAIL_SUBJECT = "Upcoming course reminder";
    private static final String MAIL_HEADER = "Hello, %s %s!";
    private static final String MAIL_BODY = """
            
            Just a quick reminder that your course %s begins tomorrow.
            Duration: %s - %s
            
            Need help to get started? Reply to this email.
            """;
    private final EmailService emailService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(EMAIL_DATE_TIME_FORMAT);

    @Override
    public void sendCourseNotifications(Course course, EmailServerProperties emailServerProperties) {
        var settings = course.getSettings();
        var startDateFormatted = formatDate(settings.getStartDate());
        var endDateFormatted = formatDate(settings.getEndDate());
        var bodyFormatted = MAIL_BODY.formatted(course.getTitle(), startDateFormatted, endDateFormatted);

        var students = course.getEnrollments()
                .stream()
                .map(CourseEnrollment::getStudent)
                .toList();

        for (var student : students) {
            var headerFormatted = MAIL_HEADER.formatted(student.getFirstName(), student.getLastName());
            var messageFormatted = headerFormatted.concat(bodyFormatted);

            emailService.send(
                    student.getEmail(),
                    MAIL_SUBJECT,
                    messageFormatted,
                    emailServerProperties);
        }
    }

    private String formatDate(LocalDateTime date) {
        return nonNull(date) ? date.format(formatter) : "*";
    }

}
