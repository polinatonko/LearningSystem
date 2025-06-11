package org.example.learningsystem.btp.saasprovisioningservice.service;

import org.example.learningsystem.btp.saasprovisioningservice.dto.ServiceInfoDto;
import org.example.learningsystem.btp.saasprovisioningservice.dto.SubscriptionRequestDto;

import java.util.List;

public interface SubscriptionService {

    String subscribe(String tenantId, SubscriptionRequestDto subscription);

    void unsubscribe(String tenantId, SubscriptionRequestDto subscription);

    List<ServiceInfoDto> getDependencies();
}
