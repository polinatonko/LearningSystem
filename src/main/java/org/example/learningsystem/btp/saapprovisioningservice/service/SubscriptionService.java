package org.example.learningsystem.btp.saapprovisioningservice.service;

import org.example.learningsystem.btp.saapprovisioningservice.dto.ServiceInfoDto;
import org.example.learningsystem.btp.saapprovisioningservice.dto.SubscriptionRequestDto;
import org.example.learningsystem.btp.saapprovisioningservice.dto.SubscriptionResponseDto;

import java.util.List;

public interface SubscriptionService {

    SubscriptionResponseDto subscribe(String tenantId, SubscriptionRequestDto subscription);

    void unsubscribe(String tenantId, SubscriptionRequestDto subscription);

    List<ServiceInfoDto> getDependencies();
}
