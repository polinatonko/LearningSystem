package org.example.learningsystem.btp.featureflagsservice.service;

import org.example.learningsystem.btp.featureflagsservice.dto.FlagDto;

public interface FeatureFlagsService {

    FlagDto getByName(String name);

    boolean getBooleanByName(String name);
}
