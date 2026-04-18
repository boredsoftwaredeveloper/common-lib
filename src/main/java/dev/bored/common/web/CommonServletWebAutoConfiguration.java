package dev.bored.common.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Auto-configures a shared servlet CORS policy for Spring MVC services
 * (profile-service, stream-service).
 * <p>
 * Picks up the policy from {@link BoredProperties} and exposes it as a
 * {@link CorsConfigurationSource} bean, which Spring Security picks up
 * automatically when services call {@code .cors(Customizer.withDefaults())}.
 * </p>
 *
 * @author Bored Software Developer
 * @since 2026-04-18
 */
@AutoConfiguration
@ConditionalOnClass(WebMvcConfigurer.class)
@EnableConfigurationProperties(BoredProperties.class)
public class CommonServletWebAutoConfiguration {

    /**
     * Shared CORS source mapped to {@code /**} so it covers every endpoint.
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
