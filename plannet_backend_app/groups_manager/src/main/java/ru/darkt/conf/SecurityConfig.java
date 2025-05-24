package ru.darkt.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    // JWT Decoder
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    // Конвертация ролей из claim realm_access.roles -> ROLE_...
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
            if (realmAccess.get("roles") instanceof Collection<?>) {
                Collection<?> roles = (Collection<?>) realmAccess.get("roles");
                return ((Collection<String>) roles).stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        });
        return converter;
    }

    // CORS-конфиг
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of(
                "http://localhost:8082",
                "http://localhost:5173",
                "http://darkt.ru",
                "https://darkt.ru",
                "http://groups_manager:8082"
        ));
        cfg.setAllowedMethods(List.of("GET","POST","PATCH","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin"
        ));
        cfg.setExposedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }

    // 1. Цепочка для Swagger/OpenAPI — без аутентификации
    @Bean
    @Order(1)
    public SecurityFilterChain swaggerSecurityChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs"
                )
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults());
        return http.build();
    }

    // 2. Основная цепочка для всего остального — с JWT
    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                        .bearerTokenResolver(new DefaultBearerTokenResolver())
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                );
        return http.build();
    }
}