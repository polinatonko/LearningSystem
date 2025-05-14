package org.example.learningsystem.btp.featureflags.service;

import org.example.learningsystem.btp.featureflags.dto.FlagDto;

public interface FeatureFlagsService {

    FlagDto getFeatureFlag(String featureName);

    boolean getBooleanFeatureFlag(String featureName);
}
