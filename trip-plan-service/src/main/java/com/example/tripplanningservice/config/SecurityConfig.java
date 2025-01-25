package com.example.tripplanningservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

public class SecurityConfig {
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");  // React frontend
        corsConfiguration.addAllowedMethod("*");  // Allow all HTTP methods (GET, POST, etc.)
        corsConfiguration.addAllowedHeader("*");  // Allow all headers
        corsConfiguration.setAllowCredentials(true);  // Allow credentials (e.g., cookies, JWT)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);  // Apply to all paths

        return source;
    }

}
