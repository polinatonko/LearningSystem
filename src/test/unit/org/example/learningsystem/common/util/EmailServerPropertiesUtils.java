package org.example.learningsystem.common.util;

import org.example.learningsystem.core.email.config.EmailServerPropertiesConfiguration;

public class EmailServerPropertiesUtils {

    private static final String FROM = "from@gmail.com";
    private static final String USER = "user";
    private static final String PASSWORD = "pwd";
    private static final String HOST = "smtp.io";
    private static final String PORT = "587";
    private static final String PROTOCOL = "smtp";
    private static final String AUTH = "true";
    private static final String STARTTLS_ENABLED = "true";

    public static EmailServerPropertiesConfiguration buildEmailServerPropertiesConfiguration() {
        return new EmailServerPropertiesConfiguration(FROM, USER, PASSWORD, HOST, PORT, PROTOCOL, AUTH, STARTTLS_ENABLED);
    }

}
