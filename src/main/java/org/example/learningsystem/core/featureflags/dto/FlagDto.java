package org.example.learningsystem.core.featureflags.dto;

public record FlagDto(Integer httpStatus, String featureName, String type, String variation) {
}