package com.smart.drop.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration that exposes an OpenAPI description for the application.
 * Configured with grouped OpenAPI definitions to segregate public frontend endpoints
 * from internal backend administrative operations, organized cleanly by Bounded Contexts.
 */
@Configuration
public class OpenApiConfiguration {

    @Value("${spring.application.name:SmartDrop}")
    String applicationName;

    @Value("${documentation.application.description:SmartDrop - IoT Liquid Monitoring & Quality Management Platform}")
    String applicationDescription;

    @Value("${documentation.application.version:1.0.0}")
    String applicationVersion;

    @Bean
    public OpenAPI smartDropOpenApi() {
        var openApi = new OpenAPI();
        openApi
                .info(new Info()
                        .title("SmartDrop - IoT Liquid Monitoring & Quality Management Platform")
                        .description("API RESTful oficial de la plataforma SmartDrop estructurada bajo principios de Domain-Driven Design (DDD). Incluye monitoreo volumétrico en tiempo real, algoritmos polimórficos de detección de fugas (patrón Strategy), normalización IoT (Factory Method) y caché en memoria de alta velocidad (Proxy Cache-Aside).")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio Oficial de la Organización SmartDrop en GitHub")
                        .url("https://github.com/orgs/smartdrop-w/repositories"));

        final String securitySchemeName = "bearerAuth";

        openApi.addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));

        return openApi;
    }

    /**
     * Grupo 1: API Pública y de cara al Frontend (Web/Móvil).
     * Contiene únicamente los endpoints de negocio consumidos por clientes finales.
     */
    @Bean
    public GroupedOpenApi frontendPublicApi() {
        return GroupedOpenApi.builder()
                .group("1-frontend-api")
                .displayName("1. Frontend & Public API (Clientes)")
                .pathsToMatch(
                        "/api/v1/iam/auth/**",
                        "/api/v1/profiles/**",
                        "/api/v1/inventory/tanks/**",
                        "/api/v1/inventory/devices/**",
                        "/api/v1/inventory/consumptions/**",
                        "/api/v1/inventory/irrigation/**",
                        "/api/v1/analytics/**",
                        "/api/v1/support/alerts/**",
                        "/api/v1/support/notifications/**",
                        "/api/v1/support/management/tickets/**",
                        "/api/v1/finance/**"
                )
                .pathsToExclude(
                        "/api/v1/administration/**",
                        "/api/v1/inventory/inventories/**"
                )
                .build();
    }

    /**
     * Grupo 2: API de Administración Interna y Mantenimiento Backend.
     * Contiene endpoints de auditoría, configuraciones globales, stock de almacén y health probes.
     */
    @Bean
    public GroupedOpenApi backendAdminApi() {
        return GroupedOpenApi.builder()
                .group("2-backend-internal-api")
                .displayName("2. Backend & Internal Administration")
                .pathsToMatch(
                        "/api/v1/administration/**",
                        "/api/v1/iam/roles/**",
                        "/api/v1/inventory/inventories/**",
                        "/api/v1/support/management/**",
                        "/api/v1/planning/**",
                        "/api/v1/health/**",
                        "/health"
                )
                .build();
    }

    /**
     * Grupo 3: Catálogo Completo (All Bounded Contexts).
     * Muestra la totalidad de los endpoints del backend organizados por sus respectivos Bounded Contexts.
     */
    @Bean
    public GroupedOpenApi completeApi() {
        return GroupedOpenApi.builder()
                .group("3-complete-platform-api")
                .displayName("3. Complete Platform API (Todos los Bounded Contexts)")
                .pathsToMatch("/api/**", "/health")
                .build();
    }
}
