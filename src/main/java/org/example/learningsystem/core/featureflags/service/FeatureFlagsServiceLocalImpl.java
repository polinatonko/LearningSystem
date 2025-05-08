package org.example.learningsystem.core.featureflags.service;

import org.example.learningsystem.core.featureflags.dto.FlagDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!cloud")
public class FeatureFlagsServiceLocalImpl implements FeatureFlagsService {

    @Override
    public FlagDto getFeatureFlag(String featureName) {
        return null;
    }

    @Override
    public boolean getBooleanFeatureFlag(String featureName) {
        return false;
    }
}
