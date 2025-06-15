package org.example.learningsystem.core.web.oauth2;

public record Oauth2ClientCredentials(String clientId, String clientSecret, String tokenUrl) {

    public Oauth2ClientCredentials withTokenUrl(String tokenUrl) {
        return new Oauth2ClientCredentials(this.clientId, this.clientSecret, tokenUrl);
    }
}
