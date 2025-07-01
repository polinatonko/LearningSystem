package org.example.learningsystem.course.job.builder;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.template.service.TemplateBuilder;
import org.example.learningsystem.core.i18n.LocalizedEmailDateTimeFormatter;
import org.example.learningsystem.course.job.model.EmailTemplateComponent;
import org.example.learningsystem.course.job.dto.CourseNotificationDto;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.student.model.Student;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

import static org.example.learningsystem.course.job.model.EmailTemplateComponent.BODY;
import static org.example.learningsystem.course.job.model.EmailTemplateComponent.FOOTER;
import static org.example.learningsystem.course.job.model.EmailTemplateComponent.HEADER;
import static org.example.learningsystem.course.job.model.EmailTemplateComponent.SUBJECT;

@Service
@RequiredArgsConstructor
public class LocalizedCourseNotificationBuilder implements CourseNotificationBuilder {

    private static final String EMAIL_TEMPLATE = "course_notification";

    private final LocalizedEmailDateTimeFormatter localizedEmailDateTimeFormatter;
    private final MessageSource messageSource;
    private final TemplateBuilder templateBuilder;

    @Override
    public CourseNotificationDto build(Course course, Student student) {
        var locale = student.getLocale();

        var subject = resolveComponent(SUBJECT, locale);
        var templateArgs = resolveTemplateArgs(course, student, locale);
        var message = templateBuilder.build(EMAIL_TEMPLATE, templateArgs);

        return new CourseNotificationDto(subject, message);
    }

    private Map<String, String> resolveTemplateArgs(Course course, Student student, Locale locale) {
        var header = resolveComponent(HEADER, locale, student.getFirstName(), student.getLastName());
        var body = resolveBody(course, locale);
        var footer = resolveComponent(FOOTER, locale);
        return Map.of(
                HEADER.getParameterName(), header,
                BODY.getParameterName(), body,
                FOOTER.getParameterName(), footer
        );
    }

    private String resolveComponent(EmailTemplateComponent component, Locale locale, Object... args) {
        var code = component.getMessageCode();
        return messageSource.getMessage(code, args, locale);
    }

    private String resolveBody(Course course, Locale locale) {
        var settings = course.getSettings();
        var startDateFormatted = localizedEmailDateTimeFormatter.format(settings.getStartDate(), locale);
        var endDateFormatted = localizedEmailDateTimeFormatter.format(settings.getEndDate(), locale);
        return resolveComponent(BODY, locale, course.getTitle(), startDateFormatted, endDateFormatted);
    }
}
