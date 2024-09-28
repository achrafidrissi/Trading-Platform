package com.tradingplatform.tradeapp.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
                        //All requests started with api need authentication
                        .requestMatchers("/api/**").authenticated()
                        //Any other request don't start with api will be permit
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf-> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                ;
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {

        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                //spécifies les origines qui sont autorisées à faire des requêtes vers ton serveur
                config.setAllowedOrigins(
                        Arrays.asList(
                                "http://localhost:5173",
                                "http://localhost:3000"
                        )
                );
                //Cette ligne permet de définir les méthodes HTTP (GET, POST, PUT, DELETE, etc.) autorisées
                config.setAllowedMethods(Collections.singletonList("*"));
                // permet de dire si les requêtes CORS sont autorisées à inclure des informations
                // d'identification (comme des cookies ou des en-têtes d'autorisation)
                config.setAllowCredentials(true);
                config.setExposedHeaders(Arrays.asList(("Authorization")));
                config.setAllowedHeaders(Collections.singletonList("*"));
                // définit la durée pendant laquelle les navigateurs peuvent mettre en cache la réponse CORS avant
                // de refaire une nouvelle requête de vérification des règles CORS(ici 1heure)
                config.setMaxAge(3600L);
                return config;
            }
        };
    }

}
