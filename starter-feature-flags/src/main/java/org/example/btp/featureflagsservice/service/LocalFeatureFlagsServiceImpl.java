package org.example.btp.featureflagsservice.service;

import org.example.btp.featureflagsservice.dto.FlagDto;

/**
 * {@link FeatureFlagsService} implementation for the local development and testing.
 */
public class LocalFeatureFlagsServiceImpl implements FeatureFlagsService {

    @Override
    public FlagDto getByName(String name) {
        return null;
    }

    @Override
    public boolean getBooleanByName(String name) {
        return false;
    }
}
