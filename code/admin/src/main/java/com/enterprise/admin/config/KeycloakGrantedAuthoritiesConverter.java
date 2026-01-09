package com.enterprise.admin.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Converter to extract Keycloak groups and roles from JWT
 * 
 * Keycloak JWT structure:
 * {
 * "groups": ["/HR", "/WAREHOUSE", "/ADMIN"],
 * "realm_access": {
 * "roles": ["admin", "user"]
 * },
 * "resource_access": {
 * "admin-service": {
 * "roles": ["manage-users"]
 * }
 * }
 * }
 */
public class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String GROUPS_CLAIM = "groups";
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String RESOURCE_ACCESS_CLAIM = "resource_access";
    private static final String ROLES_KEY = "roles";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Extract groups → ROLE_GROUP_xxx
        authorities.addAll(extractGroups(jwt));

        // Extract realm roles → ROLE_xxx
        authorities.addAll(extractRealmRoles(jwt));

        // Extract client roles → ROLE_CLIENT_xxx
        authorities.addAll(extractClientRoles(jwt));

        return authorities;
    }

    /**
     * Extract groups from JWT
     * Groups in Keycloak are like: ["/HR", "/WAREHOUSE/TEAM_A"]
     * We convert them to: ROLE_GROUP_HR, ROLE_GROUP_WAREHOUSE_TEAM_A
     */
    private Collection<GrantedAuthority> extractGroups(Jwt jwt) {
        List<String> groups = jwt.getClaimAsStringList(GROUPS_CLAIM);
        if (groups == null) {
            return Collections.emptySet();
        }

        return groups.stream()
                .map(group -> {
                    // Remove leading slash and replace remaining slashes with underscore
                    String normalized = group.startsWith("/") ? group.substring(1) : group;
                    normalized = normalized.replace("/", "_").toUpperCase();
                    return new SimpleGrantedAuthority("ROLE_GROUP_" + normalized);
                })
                .collect(Collectors.toSet());
    }

    /**
     * Extract realm roles
     */
    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim(REALM_ACCESS_CLAIM);
        if (realmAccess == null) {
            return Collections.emptySet();
        }

        List<String> roles = (List<String>) realmAccess.get(ROLES_KEY);
        if (roles == null) {
            return Collections.emptySet();
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toSet());
    }

    /**
     * Extract client roles
     */
    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractClientRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim(RESOURCE_ACCESS_CLAIM);
        if (resourceAccess == null) {
            return Collections.emptySet();
        }

        return resourceAccess.entrySet().stream()
                .flatMap(entry -> {
                    String clientId = entry.getKey().toUpperCase().replace("-", "_");
                    Map<String, Object> clientAccess = (Map<String, Object>) entry.getValue();
                    List<String> roles = (List<String>) clientAccess.get(ROLES_KEY);

                    if (roles == null) {
                        return Stream.empty();
                    }

                    return roles.stream()
                            .map(role -> new SimpleGrantedAuthority(
                                    "ROLE_" + clientId + "_" + role.toUpperCase()));
                })
                .collect(Collectors.toSet());
    }

    /**
     * Utility to get group codes from JWT (without ROLE_ prefix)
     * Used by controllers to pass to services
     */
    public static List<String> getGroupCodes(Jwt jwt) {
        List<String> groups = jwt.getClaimAsStringList(GROUPS_CLAIM);
        if (groups == null) {
            return Collections.emptyList();
        }

        return groups.stream()
                .map(group -> {
                    String normalized = group.startsWith("/") ? group.substring(1) : group;
                    return normalized.replace("/", "_").toUpperCase();
                })
                .collect(Collectors.toList());
    }
}
