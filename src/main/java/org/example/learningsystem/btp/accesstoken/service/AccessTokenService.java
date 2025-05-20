package org.example.learningsystem.btp.accesstoken.service;

public interface AccessTokenService {

    String get(String url, String clientId, String clientSecret);

    String refresh(String url, String clientId, String clientSecret);
}
