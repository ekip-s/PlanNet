package ru.darkt.conf;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;

import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Value("${springdoc.swagger-ui.oauth-authorization-url}")
    private String authorizationUrl;

    @Value("${springdoc.swagger-ui.oauth-token-url}")
    private String tokenUrl;

    @Bean
    public OpenAPI customOpenAPI(@Value("${info.server.url}") String serverUrl) {
        return new OpenAPI()
                .servers(List.of(new Server().url(serverUrl)))
                .info(new Info().title("Task Master API").version("1.0").description("API для управления задачами"))
                .components(new Components()
                        .addSecuritySchemes("oauth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl(authorizationUrl)
                                                .tokenUrl(tokenUrl)
                                        )
                                )
                        )
                );
    }
}