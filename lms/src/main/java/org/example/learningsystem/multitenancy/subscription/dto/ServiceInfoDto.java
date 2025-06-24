package org.example.learningsystem.multitenancy.subscription.dto;

/**
 * Represents a service dependency information required for SaaS Provisioning Service subscription.
 *
 * @param xsappname the xsappname property of the service that the application depends on
 */
public record ServiceInfoDto(String xsappname) {
}
