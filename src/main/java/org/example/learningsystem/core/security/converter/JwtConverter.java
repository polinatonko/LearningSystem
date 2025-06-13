package org.example.learningsystem.core.security.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String ROLE_NAME_TEMPLATE = "ROLE_%s";

    private final Converter<Jwt, AbstractAuthenticationToken> delegate;

    public JwtConverter(Converter<Jwt, AbstractAuthenticationToken> delegate) {
        this.delegate = delegate;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        var token = delegate.convert(source);

        var authorities = extractAuthorities(token);
        var roles = extractRoles(source);

        var combinedAuthorities = Stream.concat(authorities.stream(), roles.stream())
                .toList();
        log.info("Granted authorities: {}", combinedAuthorities);

        return new JwtAuthenticationToken(source, combinedAuthorities);
    }

    private Collection<GrantedAuthority> extractAuthorities(AbstractAuthenticationToken token) {
        return Optional.ofNullable(token)
                .map(AbstractAuthenticationToken::getAuthorities)
                .orElse(Collections.emptyList());
    }

    private List<GrantedAuthority> extractRoles(Jwt source) {
        var attributes = source.getClaimAsMap("xs.system.attributes");
        return Optional.ofNullable(attributes)
                .map(attrs -> (List<String>) attrs.get("xs.rolecollections"))
                .orElse(Collections.emptyList())
                .stream()
                .map(ROLE_NAME_TEMPLATE::formatted)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
