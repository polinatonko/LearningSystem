package org.example.learningsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.learningsystem.common.config.DataSourceProperties;
import org.example.learningsystem.common.config.PostgreSQLConfiguration;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Tag("integration")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EnableConfigurationProperties(DataSourceProperties.class)
@Import(PostgreSQLConfiguration.class)
@Testcontainers
public abstract class AbstractCommonIT {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
