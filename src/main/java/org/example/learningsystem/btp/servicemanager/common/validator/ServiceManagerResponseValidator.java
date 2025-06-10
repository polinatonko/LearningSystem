package org.example.learningsystem.btp.servicemanager.common.validator;

import org.example.learningsystem.btp.servicemanager.common.dto.PaginatedResponseDto;
import org.example.learningsystem.btp.servicemanager.common.exception.ServiceNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@Profile("cloud")
public class ServiceManagerResponseValidator {

    public <T> void validatePaginated(PaginatedResponseDto<T> response, String property, String propertyValue) {
        if (isNull(response) || response.numItems() == 0) {
            throw new ServiceNotFoundException(property, propertyValue);
        }
    }
}
