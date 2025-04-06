package com.caritrainc.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() } // Disable CSRF (if needed)
            .requiresChannel { it.anyRequest().requiresInsecure() } // Allows HTTP (remove if you want HTTPS)
            .authorizeHttpRequests { it.anyRequest().permitAll() }

            // Allows all requests without authentication
            .build()
    }
    @Override
     fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("GET", "POST") // Allow only specific HTTP methods
            .allowedHeaders("*") // Allow all headers
            .allowCredentials(true) // Allow credentials like cookies or HTTP authentication
    }
}
