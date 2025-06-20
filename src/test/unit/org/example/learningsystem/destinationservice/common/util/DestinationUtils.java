package org.example.learningsystem.destinationservice.common.util;

import org.example.learningsystem.btp.destinationservice.dto.DestinationDto;
import org.example.learningsystem.btp.destinationservice.dto.MailDestinationConfigurationDto;

public class DestinationUtils {

    private static final String HOST = "live.smtp.mailtrap.io";
    private static final String PORT = "587";

    public static DestinationDto buildMailDestinationDto() {
        var dtoConfiguration = new MailDestinationConfigurationDto();
        dtoConfiguration.setHost(HOST);
        dtoConfiguration.setPort(PORT);
        return new DestinationDto(null, dtoConfiguration);
    }
}
