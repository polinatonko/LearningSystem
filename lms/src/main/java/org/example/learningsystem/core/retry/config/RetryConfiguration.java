package org.example.learningsystem.core.retry.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Configuration class enabling Spring Retry functionality across the application.
 */
@Configuration
@EnableRetry
public class RetryConfiguration {
}
