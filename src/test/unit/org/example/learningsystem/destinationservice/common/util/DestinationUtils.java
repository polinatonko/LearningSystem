package org.example.learningsystem.destinationservice.common.util;

import org.example.learningsystem.btp.destinationservice.dto.MailDestinationDto;

public class DestinationUtils {

    private static final String HOST = "live.smtp.mailtrap.io";
    private static final String PORT = "587";

    public static MailDestinationDto buildMailDestinationDto() {
        var dto = new MailDestinationDto();
        dto.setHost(HOST);
        dto.setPort(PORT);
        return dto;
    }
}
