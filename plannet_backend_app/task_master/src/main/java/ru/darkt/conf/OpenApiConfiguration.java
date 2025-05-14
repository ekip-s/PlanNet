package ru.darkt.conf;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

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
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "oauth2";

        return new OpenAPI()
                .info(new Info()
                        .title("Task Master API")
                        .version("1.0")
                        .description("API для управления задачами")
                )
                // <-- вот эта строка делает все endpoints "lockable" и заставляет UI
                //    автоматически добавлять токен в Authorization заголовок
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
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