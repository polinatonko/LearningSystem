package org.example.learningsystem.btp.saasprovisioningservice.service;

import org.example.learningsystem.btp.saasprovisioningservice.dto.ServiceInfoDto;
import org.example.learningsystem.btp.saasprovisioningservice.dto.SubscriptionRequestDto;

import java.util.List;

/**
 * Interface for handling tenant subscription and unsubscription operations
 * with the SaaS Provisioning Service.
 */
public interface SubscriptionService {

    /**
     * Handles the subscription of a new tenant to the application.
     *
     * @param tenantId the unique identifier of the subscribing tenant
     * @param subscription the subscription request details
     * @return the tenant-specific application URL that will be accessible to the subscriber
     */
    String subscribe(String tenantId, SubscriptionRequestDto subscription);

    /**
     * Handles the unsubscription of a tenant from the application.
     *
     * @param tenantId the unique identifier of the subscribing tenant
     * @param subscription the subscription request details
     */
    void unsubscribe(String tenantId, SubscriptionRequestDto subscription);

    /**
     * Retrieves the list of service dependencies required by the application.
     *
     * @return a list of {@link ServiceInfoDto} objects representing required services
     */
    List<ServiceInfoDto> getDependencies();
}
