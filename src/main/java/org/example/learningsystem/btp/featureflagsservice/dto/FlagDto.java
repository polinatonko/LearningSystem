package org.example.learningsystem.btp.featureflagsservice.dto;

public record FlagDto(Integer httpStatus, String featureName, String type, String variation) {
}