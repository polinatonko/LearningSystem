package org.example.learningsystem.destinationservice.common.util;

import lombok.NoArgsConstructor;
import org.example.learningsystem.btp.destinationservice.dto.DestinationDto;
import org.example.learningsystem.btp.destinationservice.dto.MailDestinationConfigurationDto;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
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
