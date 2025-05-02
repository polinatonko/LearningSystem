package org.example.learningsystem.service.email;

import io.mailtrap.client.MailtrapClient;
import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.MailtrapMail;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.config.SendingEmailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final SendingEmailProperties emailProperties;
    private MailtrapClient client;
    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @PostConstruct
    public void postConstruct() {
        var config = new MailtrapConfig.Builder()
                .token(emailProperties.getToken())
                .build();
        this.client = MailtrapClientFactory.createMailtrapClient(config);
    }

    public void send(String to, String subject, String text) {
        var fromAddress = new Address(emailProperties.getFrom(), emailProperties.getName());
        var mail = MailtrapMail.builder()
                .from(fromAddress)
                .to(List.of(new Address(to)))
                .subject(subject)
                .text(text)
                .category("Integration Test")
                .build();
        try {
            client.send(mail);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}