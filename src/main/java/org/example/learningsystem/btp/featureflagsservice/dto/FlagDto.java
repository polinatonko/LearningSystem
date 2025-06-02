package org.example.learningsystem.btp.featureflagsservice.dto;

/**
 * Represents an instance of the feature flag.
 *
 * @param httpStatus the status of the HTTP response
 * @param featureName the name of the flag
 * @param type the type of the flag
 * @param variation the variation of the flag
 */
public record FlagDto(Integer httpStatus, String featureName, String type, String variation) {
}