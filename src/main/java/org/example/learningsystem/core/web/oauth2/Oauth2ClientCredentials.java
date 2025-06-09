package org.example.learningsystem.core.web.oauth2;

public interface Oauth2ClientCredentials {

    String getClientId();

    String getClientSecret();

    String getTokenUrl();
}
