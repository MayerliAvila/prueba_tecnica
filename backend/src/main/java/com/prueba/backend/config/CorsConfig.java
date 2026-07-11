package com.prueba.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para la API REST.
 *
 * <p>Registra un {@link CorsFilter} que permite peticiones desde cualquier origen,
 * con cualquier método HTTP y cualquier encabezado. Esta configuración es adecuada
 * para entornos de desarrollo. En producción, se recomienda restringir los orígenes permitidos.
 */
@Configuration
public class CorsConfig {

    /**
     * Define el bean {@link CorsFilter} con las reglas de CORS aplicadas globalmente
     * a todos los endpoints de la API ({@code /**}).
     *
     * @return instancia de {@link CorsFilter} configurada
     */
    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // Permitir cualquier origen (solo para desarrollo)
        config.addAllowedOriginPattern("*");

        // Permitir todos los métodos HTTP
        config.addAllowedMethod("*");

        // Permitir todos los encabezados
        config.addAllowedHeader("*");

        // Permitir envío de cookies/autenticación si en el futuro lo necesitas
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}