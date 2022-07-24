package net.medrag.microservices.security.keycloack;

import com.nimbusds.jose.shaded.json.JSONArray;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Stanislav Tretyakov
 * 12.12.2021
 */
@Profile("koauth")
@Configuration
@EnableWebSecurity
class OauthSecurityConfig extends WebSecurityConfigurerAdapter {    // doesn't work for now

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/user/**")
                        .hasRole("user")
                        .antMatchers("/admin/**")
                        .hasRole("admin")
                        .anyRequest()
                        .authenticated())
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .oidcUserService(this.oidcUserService())
                        )
                );
    }

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);

            final Map<String, Object> claims = oidcUser.getClaims();
            final JSONArray groups = (JSONArray) claims.get("groups");

            final Set<GrantedAuthority> mappedAuthorities = groups.stream()
                    .map(role -> new SimpleGrantedAuthority(("ROLE_" + role)))
                    .collect(Collectors.toSet());

            return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        };
    }
}
