package org.example.learningsystem.btp.servicemanager.common.validator;

import org.example.learningsystem.btp.servicemanager.common.dto.PaginatedResponseDto;
import org.example.learningsystem.btp.servicemanager.common.exception.ServiceNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

/**
 * Validates responses received from the Service Manager API.
 */
@Component
@Profile("cloud")
public class ServiceManagerResponseValidator {

    /**
     * Validates a paginated response: checks if the response is {@code null} or empty.
     *
     * @param response      the {@link PaginatedResponseDto} instance to validate
     * @param property      the property name that was used for searching
     * @param propertyValue the value that was searched for
     * @param <T>           the type of items contained in the response
     */
    public <T> void validatePaginated(PaginatedResponseDto<T> response, String property, String propertyValue) {
        if (isNull(response) || response.numItems() == 0) {
            throw new ServiceNotFoundException(property, propertyValue);
        }
    }
}
