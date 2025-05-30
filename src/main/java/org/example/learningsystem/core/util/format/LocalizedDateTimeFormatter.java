package org.example.learningsystem.core.util.format;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.util.Objects.nonNull;
import static org.example.learningsystem.core.util.format.DataFormatUtils.EMAIL_DATE_TIME_FORMAT;

@Service
public class LocalizedDateTimeFormatter {

    private static final String EMPTY_DATE = "*";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(EMAIL_DATE_TIME_FORMAT);

    public String format(LocalDateTime date, Locale locale) {
        return nonNull(date)
                ? date.format(formatter.withLocale(locale))
                : EMPTY_DATE;
    }

}
