package org.jono.medicalmodelsauthorizationserver.config;

import static org.jono.medicalmodelsauthorizationserver.utils.DateTimeUtils.parseTimeout;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.jono.medicalmodelsauthorizationserver.service.MmUserInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    public static final String RSA_ALGORITHM = "RSA";
    public static final int KEY_SIZE = 2048;

    @Value("${jwt.timeout:30m}")
    private String jwtTimeout;

    @Bean
    @Order(1)
    public SecurityFilterChain authzServerFilterChain(final HttpSecurity http)
            throws Exception {
        final var authzServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        http
                .securityMatcher(authzServerConfigurer.getEndpointsMatcher())
                .with(authzServerConfigurer, Customizer.withDefaults())
                .authorizeHttpRequests((authorize) ->
                                               authorize.anyRequest().authenticated()
                );

        http.cors((cors) -> cors
                .configurationSource(createCorsConfig()));

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http.exceptionHandling((exceptions) ->
                                       exceptions.defaultAuthenticationEntryPointFor(
                                               new LoginUrlAuthenticationEntryPoint("/login"),
                                               new MediaTypeRequestMatcher(MediaType.TEXT_HTML))
        );

        return http.build();
    }

    UrlBasedCorsConfigurationSource createCorsConfig() {
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity http)
            throws Exception {
        http
                .formLogin(form -> form
                        .loginPage("/login"))
                .csrf(c -> c.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(final MmUserInfoService mmUserInfoService) {
        return new InMemoryUserDetailsManager(mmUserInfoService.getUserDetails());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(@Value("${base.url}") final String baseUrl) {
        final var nextAuthClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("next-auth-client")
                .clientSecret("next-auth-client-secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUris(uris -> uris.add(baseUrl + "/api/auth/callback/my_authorization_server"))
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder()
                                        .requireProofKey(true)
                                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(nextAuthClient);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        final var keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        final KeyPair keyPair = keyPairGenerator.generateKeyPair();

        final var publicKey = (RSAPublicKey) keyPair.getPublic();
        final var privateKey = (RSAPrivateKey) keyPair.getPrivate();
        final var rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        final var jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(final MmUserInfoService mmUserInfoService) {
        return (context) -> {
            context.getClaims().expiresAt(createJwtExpirationTime());

            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                final OidcUserInfo userInfo = mmUserInfoService.loadUser(
                        context.getPrincipal().getName());
                context.getClaims().claims(claims ->
                                                   claims.putAll(userInfo.getClaims()));
            }
        };
    }

    private Instant createJwtExpirationTime() {
        final long jwtTimeoutSeconds = parseTimeout(jwtTimeout);
        return Instant.now().plusSeconds(jwtTimeoutSeconds);
    }
}
