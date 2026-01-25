package boop.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Boop Pet Care API",
                version = "1.0",
                description = "Swagger UI for Boop backend"
        )
)
public class OpenApiConfig {
        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        // This applies the security scheme to every endpoint in Swagger
                        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                        .components(new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
        }

        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                        .group("1-public-apis") // Added '1-' to force it to show first in dropdown
                        .pathsToMatch("/auth/**", "/public/**")
                        // Removes security requirement for public APIs
                        .addOpenApiCustomizer(openApi -> openApi.setSecurity(new ArrayList<>()))
                        .build();
        }

        @Bean
        public GroupedOpenApi internalApi() {
                return GroupedOpenApi.builder()
                        .group("2-internal-apis")
                        .pathsToMatch("/**")
                        .pathsToExclude("/auth/**", "/public/**")
                        .addOpenApiCustomizer(openApi -> openApi.addSecurityItem(
                                new SecurityRequirement().addList("bearerAuth")))
                        .build();
        }
}
