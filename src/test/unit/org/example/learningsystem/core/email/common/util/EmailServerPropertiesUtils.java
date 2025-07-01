package org.example.learningsystem.core.email.common.util;

import lombok.NoArgsConstructor;
import org.example.learningsystem.email.model.EmailServerPropertiesImpl;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class EmailServerPropertiesUtils {

    private static final String FROM = "from@gmail.com";
    private static final String USER = "user";
    private static final String PASSWORD = "pwd";
    private static final String HOST = "smtp.io";
    private static final String PORT = "587";
    private static final String PROTOCOL = "smtp";
    private static final String AUTH = "true";
    private static final String STARTTLS_ENABLED = "true";

    public static EmailServerPropertiesImpl buildEmailServerPropertiesConfiguration() {
        return new EmailServerPropertiesImpl(FROM, USER, PASSWORD, HOST, PORT, PROTOCOL, AUTH, STARTTLS_ENABLED);
    }
}
