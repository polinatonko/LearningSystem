package org.example.learningsystem.course.job.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTemplateComponent {

    SUBJECT("subject"),
    HEADER("header"),
    BODY("body"),
    FOOTER("footer");

    private static final String MESSAGE_CODE_TEMPLATE = "course.notifications.%s";

    private final String parameterName;

    public String getMessageCode() {
        return MESSAGE_CODE_TEMPLATE.formatted(parameterName);
    }
}
