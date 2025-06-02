package org.example.learningsystem.btp.featureflagsservice.service;

import org.example.learningsystem.btp.featureflagsservice.dto.FlagDto;

/**
 * Interface for interacting with the Feature Flags Service.
 */
public interface FeatureFlagsService {

    /**
     * Retrieves the feature flag by its name.
     *
     * @param name the name of the flag
     * @return the {@link FlagDto} instance
     */
    FlagDto getByName(String name);

    /**
     * Retrieves the boolean value of feature flag by its name.
     * <p>
     * Performs validation of the received feature flag and converts the variation to
     * {@code boolean} value.
     *
     * @param name the name of the flag
     * @return the boolean state of the flag or {@code false} if invalid or not found
     */
    boolean getBooleanByName(String name);
}
