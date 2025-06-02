package org.example.learningsystem.btp.featureflagsservice.validator;

import org.example.learningsystem.btp.featureflagsservice.dto.FlagDto;

/**
 * Interface for validating {@link FlagDto} instances.
 */
public interface FeatureFlagsValidator {

    /**
     * Returns whether the {@link FlagDto} instance is valid or not.
     *
     * @param flag         the {@link FlagDto} instance
     * @param requiredType required type of the flag
     * @return {@literal true} if the {@link FlagDto} is valid.
     */
    boolean isValid(FlagDto flag, String requiredType);
}
