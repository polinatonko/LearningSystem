package org.example.learningsystem.core.web.oauth2;

/**
 * Interface for OAuth 2.0 token management operations.
 */
public interface Oauth2TokenClient {

    /**
     * Retrieves an OAuth 2.0 access token using client credentials grant.
     *
     * @param url          the URL of the authorization server
     * @param clientId     the client identifier issued by the authorization server
     * @param clientSecret the client secret associated with the client identifier
     * @return the access token
     */
    String get(String url, String clientId, String clientSecret);

    /**
     * Refreshes an OAuth 2.0 access token using client credentials grant.
     *
     * @param url          the URL of the authorization server
     * @param clientId     the client identifier issued by the authorization server
     * @param clientSecret the client secret associated with the client identifier
     * @return the access token
     */
    String refresh(String url, String clientId, String clientSecret);
}
