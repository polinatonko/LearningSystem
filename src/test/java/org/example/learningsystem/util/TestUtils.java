package org.example.learningsystem.util;

import org.example.learningsystem.core.security.config.UserCredentials;
import org.springframework.http.HttpHeaders;

public class TestUtils {

    public static HttpHeaders buildBasicAuthenticationHeader(UserCredentials userCredentials) {
        var headers = new HttpHeaders();
        headers.setBasicAuth(userCredentials.username(), userCredentials.password());
        return headers;
    }

}
