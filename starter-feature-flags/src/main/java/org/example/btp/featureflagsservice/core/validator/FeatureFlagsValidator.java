package org.example.btp.featureflagsservice.core.validator;

import org.example.btp.featureflagsservice.core.dto.FlagDto;

/**
 * Interface for validating {@link FlagDto} instances.
 */
public interface FeatureFlagsValidator {

    /**
     * Returns whether the {@link FlagDto} instance is valid or not.
     *
     * @param flag         the {@link FlagDto} instance to validate
     * @param requiredType the expected type of the feature flag
     * @return {@literal true} if the {@link FlagDto} is valid
     */
    boolean isValid(FlagDto flag, String requiredType);
}
