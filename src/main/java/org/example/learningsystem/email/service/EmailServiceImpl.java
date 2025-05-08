package org.example.learningsystem.email.service;

import io.mailtrap.client.MailtrapClient;
import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.MailtrapMail;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.email.config.EmailServerProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailServerProperties emailServerProperties;
    private final MailtrapClient client;
    private final static String EMAIL_CATEGORY = "Integration test";

    public EmailServiceImpl(
            EmailServerProperties emailServerProperties,
            MailtrapConfig mailtrapConfig) {
        this.emailServerProperties = emailServerProperties;
        this.client = MailtrapClientFactory.createMailtrapClient(mailtrapConfig);
    }

    public void send(String to, String subject, String text) {
        var mail = buildMail(to, subject, text);
        trySendMail(mail);
    }

    private void trySendMail(MailtrapMail mail) {
        try {
            client.send(mail);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private MailtrapMail buildMail(String to, String subject, String text) {
        var fromAddress = new Address(emailServerProperties.getFrom(), emailServerProperties.getName());
        return MailtrapMail.builder()
                .from(fromAddress)
                .to(List.of(new Address(to)))
                .subject(subject)
                .text(text)
                .category(EMAIL_CATEGORY)
                .build();
    }
}