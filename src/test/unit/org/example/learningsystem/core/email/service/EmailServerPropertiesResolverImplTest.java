package org.example.learningsystem.core.email.service;

import org.example.learningsystem.btp.destinationservice.dto.MailDestinationDto;
import org.example.learningsystem.btp.destinationservice.service.DestinationService;
import org.example.learningsystem.btp.featureflagsservice.service.FeatureFlagsService;
import org.example.learningsystem.core.email.config.EmailServerProperties;
import org.example.learningsystem.core.email.config.EmailServerPropertiesConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.learningsystem.common.DestinationUtils.buildMailDestinationDto;
import static org.example.learningsystem.common.EmailServerPropertiesUtils.buildEmailServerPropertiesConfiguration;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class EmailServerPropertiesResolverImplTest {

    @Mock
    private DestinationService destinationService;
    @Mock
    private FeatureFlagsService featureFlagsService;
    private EmailServerProperties emailServerProperties;
    private EmailServerPropertiesResolverImpl emailServerPropertiesResolver;

    @BeforeEach
    void setUp() {
        emailServerProperties = buildEmailServerPropertiesConfiguration();
        emailServerPropertiesResolver = new EmailServerPropertiesResolverImpl(
                destinationService, featureFlagsService, emailServerProperties);
    }

    @Test
    void resolve_whenDestinationServiceEnabled_shouldReturnMailDestinationDto() {
        // given
        when(featureFlagsService.getBooleanByName(any()))
                .thenReturn(true);

        var mailDestinationDto = buildMailDestinationDto();
        when(destinationService.getByName(any()))
                .thenReturn(mailDestinationDto);

        // when
        var resolvedEmailServerProperties = emailServerPropertiesResolver.resolve();

        // then
        assertInstanceOf(MailDestinationDto.class, resolvedEmailServerProperties);
        assertAll(
                () -> assertEquals(mailDestinationDto.getHost(), resolvedEmailServerProperties.getHost()),
                () -> assertEquals(mailDestinationDto.getPort(), resolvedEmailServerProperties.getPort())
        );
    }

    @Test
    void resolve_whenDestinationServiceDisabled_shouldReturnEmailServerPropertiesConfiguration() {
        // given
        when(featureFlagsService.getBooleanByName(any()))
                .thenReturn(false);

        // when
        var resolvedEmailServerProperties = emailServerPropertiesResolver.resolve();

        // then
        assertInstanceOf(EmailServerPropertiesConfiguration.class, resolvedEmailServerProperties);
        assertAll(
                () -> assertEquals(emailServerProperties.getHost(), resolvedEmailServerProperties.getHost()),
                () -> assertEquals(emailServerProperties.getPort(), resolvedEmailServerProperties.getPort())
        );
    }

}
