package org.example.learningsystem.core.web.oauth2;

public interface Oauth2TokenClient {

    String get(String url, String clientId, String clientSecret);

    String refresh(String url, String clientId, String clientSecret);
}
