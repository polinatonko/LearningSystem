package org.example.learningsystem.core.security.config;

import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Profile("cloud")
public class JwtConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String XS_SYSTEM_ATTRIBUTES = "xs.system.attributes";
    private static final String XS_ROLE_COLLECTIONS = "xs.rolecollections";
    private static final String SCOPE = "scope";
    private static final String ROLE_NAME_TEMPLATE = "ROLE_%s";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        var roleCollections = jwt.getClaimAsMap(XS_SYSTEM_ATTRIBUTES).get(XS_ROLE_COLLECTIONS).toString();
        var roles = toList(roleCollections);

        Collection<String> scopes = jwt.getClaimAsStringList(SCOPE);
        roles.forEach(role -> scopes.add(ROLE_NAME_TEMPLATE.formatted(role)));

        return scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private List<String> toList(String source) {
        var str = source.replaceAll("[\\[\\]]", "");
        return List.of(str.split(", "));
    }
}
