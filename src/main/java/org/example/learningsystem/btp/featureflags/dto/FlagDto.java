package org.example.learningsystem.btp.featureflags.dto;

public record FlagDto(Integer httpStatus, String featureName, String type, String variation) {
}