package org.example.learningsystem.core.email.format;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.util.Objects.nonNull;

@Service
public class LocalizedEmailDateTimeFormatter {

    private static final String EMPTY_DATE = "*";
    private static final String DATE_TIME_FORMAT = "LLLL d h:mm a";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public String format(LocalDateTime date, Locale locale) {
        return nonNull(date)
                ? date.format(formatter.withLocale(locale))
                : EMPTY_DATE;
    }

}
