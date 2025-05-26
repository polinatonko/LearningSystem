package org.example.learningsystem.email.config;

import io.mailtrap.config.MailtrapConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmailClientConfiguration {

    private final EmailServerProperties emailServerProperties;

    @Bean
    public MailtrapConfig mailtrapConfig() {
        return new MailtrapConfig.Builder()
                .token(emailServerProperties.getToken())
                .build();
    }
}
