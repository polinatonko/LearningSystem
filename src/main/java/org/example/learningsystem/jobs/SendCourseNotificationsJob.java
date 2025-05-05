package org.example.learningsystem.jobs;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.config.CourseReminderProperties;
import org.example.learningsystem.domain.Enrollment;
import org.example.learningsystem.service.course.CourseService;
import org.example.learningsystem.service.email.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.nonNull;
import static org.example.learningsystem.utils.DataFormatUtils.EMAIL_DATE_TIME_FORMAT;

@Component
@RequiredArgsConstructor
public class SendCourseNotificationsJob {

    private final CourseService courseService;
    private final EmailService emailService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(EMAIL_DATE_TIME_FORMAT);
    private final CourseReminderProperties reminderProperties;
    private static final String MAIL_SUBJECT = "Upcoming course reminder";
    private static final String MAIL_HEADER = "Hello, %s %s!";
    private static final String MAIL_BODY = """
            
            Just a quick reminder that your course %s begins tomorrow.
            Duration: %s - %s
            
            Need help to get started? Reply to this email.
            """;

    @Scheduled(cron = "#{courseReminderProperties.cron}")
    public void execute() {
        if (!reminderProperties.getEnabled()) {
            return;
        }

        var upcomingCourses = courseService.getUpcoming(reminderProperties.getDaysBefore());
        for (var course : upcomingCourses) {
            var settings = course.getSettings();
            var startDateFormatted = formatDate(settings.getStartDate());
            var endDateFormatted = formatDate(settings.getEndDate());
            var bodyFormatted = MAIL_BODY.formatted(course.getTitle(), startDateFormatted, endDateFormatted);

            var students = course.getEnrollments()
                    .stream()
                    .map(Enrollment::getStudent)
                    .toList();

            for (var student : students) {
                var headerFormatted = MAIL_HEADER.formatted(student.getFirstName(), student.getLastName());
                var messageFormatted = headerFormatted.concat(bodyFormatted);

                emailService.send(
                        student.getEmail(),
                        MAIL_SUBJECT,
                        messageFormatted);
            }
        }
    }

    private String formatDate(LocalDateTime date) {
        return nonNull(date) ? date.format(formatter) : "*";
    }
}
