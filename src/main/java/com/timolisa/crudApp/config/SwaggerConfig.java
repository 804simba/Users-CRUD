package com.timolisa.crudApp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("User CRUD API").description("User CRUD app"));
    }
    @Bean
    public GroupedOpenApi usersEndPoints() {
        return GroupedOpenApi.builder()
                .group("Users")
                .pathsToMatch("/api/**")
                .build();
    }
}
