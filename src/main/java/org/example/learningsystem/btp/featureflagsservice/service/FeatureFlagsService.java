package org.example.learningsystem.btp.featureflagsservice.service;

import org.example.learningsystem.btp.featureflagsservice.dto.FlagDto;

/**
 * Interface for interacting with the Feature Flags Service API.
 */
public interface FeatureFlagsService {

    /**
     * Returns the feature flag by its name.
     *
     * @param name name of the flag
     * @return {@link FlagDto} instance
     */
    FlagDto getByName(String name);

    /**
     * Returns the boolean feature flag by its name.
     *
     * @param name name of the flag
     * @return {@link FlagDto} instance
     */
    boolean getBooleanByName(String name);
}
