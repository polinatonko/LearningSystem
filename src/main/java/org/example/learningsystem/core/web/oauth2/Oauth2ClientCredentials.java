package org.example.learningsystem.core.web.oauth2;

public record Oauth2ClientCredentials(String clientId, String clientSecret, String tokenUrl) {
}
