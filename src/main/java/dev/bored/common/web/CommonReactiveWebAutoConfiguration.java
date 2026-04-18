package dev.bored.common.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Auto-configures a shared reactive CORS policy for WebFlux services
 * (api-gateway).
 *
 * @author Bored Software Developer
 * @since 2026-04-18
 */
@AutoConfiguration
@ConditionalOnClass(WebFluxConfigurer.class)
@EnableConfigurationProperties(BoredProperties.class)
public class CommonReactiveWebAutoConfiguration {

    /**
     * Reactive CORS source mapped to {@code /**} so it covers both proxied
     * routes and local controllers.
     */
    @Bean
    @ConditionalOnMissingBean(name = "corsConfigurationSource")
    public CorsConfigurationSource corsConfigurationSource(BoredProperties props) {
        BoredProperties.Cors cors = props.cors().withDefaults();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(cors.origins());
        config.setAllowedMethods(cors.methods());
        config.setAllowedHeaders(cors.headers());
        config.setAllowCredentials(cors.allowCredentials());
        config.setMaxAge(cors.maxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
