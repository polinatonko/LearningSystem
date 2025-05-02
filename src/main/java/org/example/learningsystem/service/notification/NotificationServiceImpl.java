package org.example.learningsystem.service.notification;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.config.CourseReminderProperties;
import org.example.learningsystem.service.course.CourseService;
import org.example.learningsystem.service.email.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

import static org.example.learningsystem.utils.DataFormatUtils.EMAIL_DATE_TIME_FORMAT;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final CourseService courseService;
    private final EmailService emailService;
    private final CourseReminderProperties reminderProperties;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(EMAIL_DATE_TIME_FORMAT);

    @Override
    @Scheduled(cron = "#{courseReminderProperties.cron}")
    public void notifyAboutUpcomingCourses() {
        if (!reminderProperties.getEnabled()) {
            return;
        }

        var emailSubject = "Upcoming course reminder";
        var emailTemplate = """
                Hello, %s!
                Just a quick reminder that your course %s begins tomorrow.
                Duration: %s - %s
                
                Need help to get started? Reply to this email.
                """;

        var upcomingCourses = courseService.getUpcoming(reminderProperties.getDaysBefore());
        for (var course : upcomingCourses) {
            var settings = course.getSettings();
            var startDateFormatted = settings.getStartDate().format(formatter);
            var endDateFormatted = "*";
            if (settings.getEndDate() != null) {
                endDateFormatted = settings.getEndDate().format(formatter);
            }

            for (var enrollments : course.getEnrollments()) {
                var student = enrollments.getStudent();
                emailService.send(student.getEmail(), emailSubject,
                        String.format(
                                emailTemplate,
                                String.format("%s %s", student.getFirstName(), student.getLastName()),
                                course.getTitle(),
                                startDateFormatted,
                                endDateFormatted));
            }
        }
    }
}
