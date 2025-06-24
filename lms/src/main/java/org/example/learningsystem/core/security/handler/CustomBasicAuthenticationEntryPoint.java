package org.example.learningsystem.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Qualifier("basicAuth")
public class CustomBasicAuthenticationEntryPoint extends AuthenticationEntryPointImpl {

    private static final String WWW_AUTHENTICATE_HEADER = "WWW-Authenticate";
    private static final String WWW_AUTHENTICATE_HEADER_VALUE = "Basic realm=\"Realm\"";

    public CustomBasicAuthenticationEntryPoint(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        super.commence(request, response, authException);
        response.setHeader(WWW_AUTHENTICATE_HEADER, WWW_AUTHENTICATE_HEADER_VALUE);
    }
}
