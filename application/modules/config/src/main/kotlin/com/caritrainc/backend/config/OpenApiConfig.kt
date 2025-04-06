package com.caritrainc.backend.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI().info(
            Info().title("Caritra API")
                .description("API documentation for caritra application")
                .version("v2.0")
        )
    }
}