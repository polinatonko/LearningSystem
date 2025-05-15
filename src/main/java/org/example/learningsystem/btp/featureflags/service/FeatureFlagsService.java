package org.example.learningsystem.btp.featureflags.service;

import org.example.learningsystem.btp.featureflags.dto.FlagDto;

public interface FeatureFlagsService {

    FlagDto getByName(String name);

    boolean getBooleanByName(String name);
}
