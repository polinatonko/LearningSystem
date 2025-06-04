package org.example.learningsystem.btp.featureflagsservice.exception;

/**
 * Exception thrown when a feature flag type mismatch error is encountered (the requested flag type does not match
 * the actual one).
 */
public class FeatureFlagTypeMismatchException extends UnsupportedOperationException {

    /**
     * Constructor for the {@link FeatureFlagTypeMismatchException}.
     *
     * @param flagType    the type of the requested flag
     * @param featureName the name of the feature
     */
    public FeatureFlagTypeMismatchException(String flagType, String featureName) {
        super("Requested feature flag must be of type %s [featureName = %s]"
                .formatted(flagType, featureName));
    }
}
