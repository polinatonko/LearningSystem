package org.example.learningsystem.btp.accesstoken.service;

public interface AccessTokenService {

    String getAccessTokenCacheable(String url, String clientId, String clientSecret);

    void evictCache(String clientId);
}
