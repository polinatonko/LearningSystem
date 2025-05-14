package org.example.learningsystem.btp.featureflags.service;

import org.example.learningsystem.btp.featureflags.dto.FlagDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!cloud")
public class FeatureFlagsServiceLocalImpl implements FeatureFlagsService {

    @Override
    public FlagDto getFeatureFlag(String name) {
        return null;
    }

    @Override
    public boolean getBooleanFeatureFlag(String name) {
        return false;
    }
}
