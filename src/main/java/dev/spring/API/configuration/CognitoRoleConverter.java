package dev.spring.API.configuration;


import dev.spring.API.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CognitoRoleConverter implements Converter<
        Jwt, Collection<GrantedAuthority>> {

    private static final String GROUPS_CLAIM = "cognito:groups";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<String> groups = jwt.getClaimAsStringList(GROUPS_CLAIM);

        if (groups == null || groups.isEmpty()) {
            return List.of();
        }

        return groups.stream()
                .map(Role::valueOf)              // ADMIN, USER -> enum
                .map(Role::authority)            // -> ROLE_ADMIN, ROLE_USER
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

