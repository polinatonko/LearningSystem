package org.example.learningsystem.core.audit.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

import static org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.CLIENT_ID;

/**
 * {@link AuditorAware} implementation which retrieves user details from the Basic or Bearer Authentication token.
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    private static final String USERNAME_CLAIM = "user_name";

    @Override
    public Optional<String> getCurrentAuditor() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();
        return extractUsername(authentication);
    }

    private Optional<String> extractUsername(Authentication authentication) {
        return authentication instanceof JwtAuthenticationToken jwtToken
                ? extractUsernameFromJwt(jwtToken)
                : extractUsernameFromUserPrincipal(authentication);
    }

    private Optional<String> extractUsernameFromJwt(JwtAuthenticationToken jwtToken) {
        var claims = jwtToken.getTokenAttributes();
        var name = claims.containsKey(USERNAME_CLAIM)
                ? claims.get(USERNAME_CLAIM)
                : claims.get(CLIENT_ID);
        return Optional.ofNullable((String) name);
    }

    private Optional<String> extractUsernameFromUserPrincipal(Authentication authentication) {
        return Optional.of(authentication)
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                .map(User::getUsername);
    }
}
