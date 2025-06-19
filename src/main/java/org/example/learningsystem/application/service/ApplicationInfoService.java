package org.example.learningsystem.application.service;

import org.example.learningsystem.application.dto.ApplicationInfoDto;

/**
 * Interface for retrieving application information.
 * <p>
 * Implementations should provide specific details about the application depending on
 * the runtime environment (e.g., cloud).
 */
public interface ApplicationInfoService {

    /**
     * Returns information about the current application.
     *
     * @return a {@link ApplicationInfoDto} instance
     */
    ApplicationInfoDto get();
}
