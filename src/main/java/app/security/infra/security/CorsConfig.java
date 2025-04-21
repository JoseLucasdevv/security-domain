package app.security.infra.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
public class CorsConfig {
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
         CorsConfiguration configuration  = new CorsConfiguration();
        configuration.addAllowedOrigin("http://127.0.0.1:3000");
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
