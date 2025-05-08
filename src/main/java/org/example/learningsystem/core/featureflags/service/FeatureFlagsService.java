package org.example.learningsystem.core.featureflags.service;

import org.example.learningsystem.core.featureflags.dto.FlagDto;

public interface FeatureFlagsService {

    FlagDto getFeatureFlag(String featureName);

    boolean getBooleanFeatureFlag(String featureName);
}
