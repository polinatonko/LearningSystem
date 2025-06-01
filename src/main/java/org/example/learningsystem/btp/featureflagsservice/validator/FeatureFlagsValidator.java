package org.example.learningsystem.btp.featureflagsservice.validator;

import org.example.learningsystem.btp.featureflagsservice.dto.FlagDto;

public interface FeatureFlagsValidator {

    boolean isValid(FlagDto flag, String requiredType);
}
