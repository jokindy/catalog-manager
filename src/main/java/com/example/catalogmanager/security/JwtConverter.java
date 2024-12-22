package com.example.catalogmanager.security;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
      new JwtGrantedAuthoritiesConverter();
  private final JwtConverterProperties properties;
  private final Logger log = LoggerFactory.getLogger(JwtConverter.class);

  public JwtConverter(JwtConverterProperties properties) {
    this.properties = properties;
  }

  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {
    Collection<GrantedAuthority> authorities =
        Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream())
            .collect(Collectors.toSet());
    log.debug("Extracted authorities: {}", authorities);
    return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
  }

  private String getPrincipalClaimName(Jwt jwt) {
    String claimName = JwtClaimNames.SUB;
    if (properties.getPrincipalAttribute() != null) {
      claimName = properties.getPrincipalAttribute();
    }
    return jwt.getClaim(claimName);
  }

  @SuppressWarnings("unchecked")
  private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
    Map<String, Object> resourceAccess = jwt.getClaim("realm_access");
    log.debug("Resource access: {}", resourceAccess);

    if (isNull(resourceAccess) || isNull(resourceAccess.get("roles"))) {
      return Set.of();
    }

    Collection<String> roles = (Collection<String>) resourceAccess.get("roles");

    log.debug("Roles for resource {}: {}", properties.getResourceId(), roles);

    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toSet());
  }
}
