package org.example.learningsystem.core.security.util;

import com.sap.cloud.security.xsuaa.token.AuthenticationToken;
import com.sap.cloud.security.xsuaa.token.XsuaaToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SecurityUtils {

    public static Optional<XsuaaToken> retrieveXsuaaTokenFromRequest(HttpServletRequest request) {
        var userPrincipalOpt = Optional.ofNullable(request.getUserPrincipal());
        return userPrincipalOpt.filter(it -> it instanceof AuthenticationToken)
                .map(it -> (XsuaaToken) ((AuthenticationToken) it).getPrincipal());
    }
}
