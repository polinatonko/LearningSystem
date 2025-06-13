package org.example.learningsystem.btp.saasprovisioningservice.dto;

/**
 * Represents a service dependency information required for SaaS Provisioning Service subscription.
 *
 * @param xsappname the xsappname property of the service that the application depends on
 */
public record ServiceInfoDto(String xsappname) {
}
