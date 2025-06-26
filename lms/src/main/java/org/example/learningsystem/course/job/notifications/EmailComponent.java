package org.example.learningsystem.course.job.notifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
enum EmailComponent {

    SUBJECT("subject"),
    HEADER("header"),
    BODY("body"),
    FOOTER("footer");

    private static final String MESSAGE_CODE_TEMPLATE = "course.reminder.%s";

    private final String parameterName;

    public String getMessageCode() {
        return MESSAGE_CODE_TEMPLATE.formatted(parameterName);
    }
}
