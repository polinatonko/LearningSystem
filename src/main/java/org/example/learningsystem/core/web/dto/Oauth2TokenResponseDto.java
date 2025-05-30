package org.example.learningsystem.core.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Oauth2TokenResponseDto(String accessToken, String tokenType, Integer expiresIn, String scope, String jti) {
}
