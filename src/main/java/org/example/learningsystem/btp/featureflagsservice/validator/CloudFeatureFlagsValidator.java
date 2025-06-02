package org.example.learningsystem.btp.featureflagsservice.validator;

import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.featureflagsservice.dto.FlagDto;
import org.example.learningsystem.btp.featureflagsservice.exception.FeatureFlagTypeMismatchException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.OK;

/**
 * Cloud {@link FeatureFlagsValidator} implementation.
 */
@Profile("cloud")
@Component
@Slf4j
public class CloudFeatureFlagsValidator implements FeatureFlagsValidator {

    @Override
    public boolean isValid(FlagDto flag, String requiredType) {
        if (isNull(flag) || flag.httpStatus() != OK.value()) {
            log.error("Received invalid feature flag: {}", flag);
            return false;
        }
        validateType(flag, requiredType);
        return true;
    }

    /**
     * Checks whether the {@link FlagDto} instance has the required type.
     *
     * @param flag         the {@link FlagDto} instance
     * @param requiredType required type of the flag
     */
    private void validateType(FlagDto flag, String requiredType) {
        var type = flag.type();
        if (!type.equals(requiredType)) {
            throw new FeatureFlagTypeMismatchException(requiredType, flag.featureName());
        }
    }

}
