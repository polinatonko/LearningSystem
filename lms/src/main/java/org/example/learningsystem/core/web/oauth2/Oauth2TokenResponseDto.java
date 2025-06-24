package org.example.learningsystem.core.web.oauth2;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

/**
 * Represents an OAuth 2.0 token response from the authorization server.
 *
 * @param accessToken the access token issued by the authorization server
 * @param tokenType   the type of the token (e.g., "bearer")
 * @param expiresIn   the lifetime of the token in seconds
 * @param scope       the scope of the token
 * @param jti         the JWT ID of the token
 */
@JsonNaming(SnakeCaseStrategy.class)
public record Oauth2TokenResponseDto(String accessToken, String tokenType, Integer expiresIn, String scope,
                                     String jti) {
}
