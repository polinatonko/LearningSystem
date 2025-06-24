package org.example.btp.featureflagsservice.validator;

import lombok.extern.slf4j.Slf4j;
import org.example.btp.featureflagsservice.dto.FlagDto;
import org.example.btp.featureflagsservice.exception.FeatureFlagTypeMismatchException;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.OK;

/**
 * Cloud {@link FeatureFlagsValidator} implementation.
 * <p>
 * Performs {@code null} and HTTP status checks and type safety verification.
 */
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

    private void validateType(FlagDto flag, String requiredType) {
        var type = flag.type();
        if (!type.equals(requiredType)) {
            throw new FeatureFlagTypeMismatchException(requiredType, flag.featureName());
        }
    }
}
