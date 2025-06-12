package org.example.learningsystem.core.security.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

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
        var authorities = new ArrayList<GrantedAuthority>();
        if (nonNull(token)) {
            authorities.addAll(token.getAuthorities());
            log.info("Jwt Authorities: {}", authorities);
            var roles = fetchRoles(source);
            log.info("Jwt Roles: {}", roles);
            authorities.addAll(roles);
        }
        return new JwtAuthenticationToken(source, authorities);
    }

    private List<GrantedAuthority> fetchRoles(Jwt source) {
        var roles = new ArrayList<GrantedAuthority>();
        var extAttributes = source.getClaimAsMap("xs.system.attributes");
        if (nonNull(extAttributes)) {
            var roleCollections = (List<String>)extAttributes.get("xs.rolecollections");
            if (nonNull(roleCollections)) {
                var rolesList = roleCollections.stream()
                        .map(ROLE_NAME_TEMPLATE::formatted)
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                roles.addAll(rolesList);
            }
        }
        return roles;
    }
}
