package org.example.learningsystem.course.job.notifications;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.template.service.RenderTemplateService;
import org.example.learningsystem.core.util.format.LocalizedDateTimeFormatter;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.student.model.Student;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

import static org.example.learningsystem.course.job.notifications.EmailComponent.BODY;
import static org.example.learningsystem.course.job.notifications.EmailComponent.FOOTER;
import static org.example.learningsystem.course.job.notifications.EmailComponent.HEADER;
import static org.example.learningsystem.course.job.notifications.EmailComponent.SUBJECT;

@Service
@RequiredArgsConstructor
public class LocalizedCourseNotificationBuilder implements CourseNotificationBuilder {

    private static final String EMAIL_TEMPLATE = "course_notification";

    private final LocalizedDateTimeFormatter localizedDateTimeFormatter;
    private final MessageSource messageSource;
    private final RenderTemplateService renderTemplateService;

    @Override
    public CourseNotificationDto build(Course course, Student student) {
        var locale = student.getLocale();

        var subject = resolveSubject(locale);
        var message = renderMessage(course, student, locale);

        return new CourseNotificationDto(subject, message);
    }

    private String renderMessage(Course course, Student student, Locale locale) {
        var arguments = resolveTemplateArguments(course, student, locale);
        return renderTemplateService.render(EMAIL_TEMPLATE, arguments);
    }

    private Map<String, String> resolveTemplateArguments(Course course, Student student, Locale locale) {
        var header = resolveHeader(student, locale);
        var body = resolveBody(course, locale);
        var footer = resolveFooter(locale);
        return Map.of(
                HEADER.getParameterName(), header,
                BODY.getParameterName(), body,
                FOOTER.getParameterName(), footer
        );
    }

    private String resolveComponent(EmailComponent component, Object[] arguments, Locale locale) {
        var code = component.getMessageCode();
        return messageSource.getMessage(code, arguments, locale);
    }

    private String resolveSubject(Locale locale) {
        return resolveComponent(SUBJECT, null, locale);
    }

    private String resolveHeader(Student student, Locale locale) {
        var arguments = new Object[]{
                student.getFirstName(), student.getLastName()
        };
        return resolveComponent(HEADER, arguments, locale);
    }

    private String resolveBody(Course course, Locale locale) {
        var settings = course.getSettings();
        var startDateFormatted = localizedDateTimeFormatter.format(settings.getStartDate(), locale);
        var endDateFormatted = localizedDateTimeFormatter.format(settings.getEndDate(), locale);
        var arguments = new Object[]{
                course.getTitle(), startDateFormatted, endDateFormatted
        };
        return resolveComponent(BODY, arguments, locale);
    }

    private String resolveFooter(Locale locale) {
        return resolveComponent(FOOTER, null, locale);
    }

}
