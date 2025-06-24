package org.example.learningsystem.core.web.oauth2;

/**
 * Interface for OAuth 2.0 token management operations.
 */
public interface Oauth2TokenClient {

    /**
     * Retrieves an OAuth 2.0 access token using client credentials grant.
     *
     * @param clientCredentials OAuth 2.0 client credentials
     * @return the access token
     */
    String get(Oauth2ClientCredentials clientCredentials);

    /**
     * Refreshes an OAuth 2.0 access token using client credentials grant.
     *
     * @param clientCredentials OAuth 2.0 client credentials
     * @return the access token
     */
    String refresh(Oauth2ClientCredentials clientCredentials);
}
