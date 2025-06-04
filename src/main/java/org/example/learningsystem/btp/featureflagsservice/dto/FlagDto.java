package org.example.learningsystem.btp.featureflagsservice.dto;

/**
 * Represents a feature flag evaluation response.
 *
 * @param httpStatus the HTTP status code of the evaluation response
 * @param featureName the name of the flag
 * @param type the type of the flag (e.g., "BOOLEAN", "STRING", etc.)
 * @param variation the variation of the flag
 */
public record FlagDto(Integer httpStatus, String featureName, String type, String variation) {
}