package BankODC.BankODC.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BankODC API")
                        .version("1.0.0")
                        .description("API REST pour le système de gestion bancaire BankODC")
                        .contact(new Contact()
                                .name("Équipe de développement BankODC")
                                .email("contact@bankodc.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .tags(Arrays.asList(
                    new Tag().name("Authentication").description("API d'authentification JWT"),
                    new Tag().name("Comptes").description("API de gestion des comptes bancaires"),
                    new Tag().name("Transactions").description("API de gestion des transactions bancaires"),
                    new Tag().name("Clients").description("API de gestion des clients"),
                    new Tag().name("Administrateurs").description("API de gestion des administrateurs"),
                    new Tag().name("Utilisateurs").description("API de gestion des utilisateurs")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT Authorization header using the Bearer scheme. Example: \"Authorization: Bearer {token}\"")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Authentication")
                .pathsToMatch("/api/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi comptesApi() {
        return GroupedOpenApi.builder()
                .group("Comptes")
                .pathsToMatch("/api/v1/comptes/**")
                .build();
    }

    @Bean
    public GroupedOpenApi transactionsApi() {
        return GroupedOpenApi.builder()
                .group("Transactions")
                .pathsToMatch("/api/transactions/**")
                .build();
    }

    @Bean
    public GroupedOpenApi clientsApi() {
        return GroupedOpenApi.builder()
                .group("Clients")
                .pathsToMatch("/api/clients/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminsApi() {
        return GroupedOpenApi.builder()
                .group("Administrateurs")
                .pathsToMatch("/api/admins/**")
                .build();
    }

    @Bean
    public GroupedOpenApi usersApi() {
        return GroupedOpenApi.builder()
                .group("Utilisateurs")
                .pathsToMatch("/api/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi allApis() {
        return GroupedOpenApi.builder()
                .group("All")
                .pathsToMatch("/api/**")
                .displayName("Toutes les APIs")
                .build();
    }
}