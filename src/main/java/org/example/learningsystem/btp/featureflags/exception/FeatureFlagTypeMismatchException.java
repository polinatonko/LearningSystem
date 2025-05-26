package org.example.learningsystem.btp.featureflags.exception;

public class FeatureFlagTypeMismatchException extends UnsupportedOperationException {

    public FeatureFlagTypeMismatchException(String flagType, String featureName) {
        super("Requested feature flag must be of type %s [featureName = %s]"
                .formatted(flagType, featureName));
    }

}
