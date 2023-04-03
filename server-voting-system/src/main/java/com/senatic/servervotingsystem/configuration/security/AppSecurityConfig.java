package com.senatic.servervotingsystem.configuration.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.senatic.servervotingsystem.configuration.constant.SecurityConstants;
import com.senatic.servervotingsystem.configuration.security.filter.CsrfCookieFilter;
import com.senatic.servervotingsystem.configuration.security.filter.JWTTokenGeneratorFilter;
import com.senatic.servervotingsystem.configuration.security.filter.JWTTokenValidatorFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppSecurityConfig {
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        CsrfTokenRequestAttributeHandler csrfRequestHandler = new CsrfTokenRequestAttributeHandler();
        csrfRequestHandler.setCsrfRequestAttributeName("_csrf");

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .cors().configurationSource(new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("*"));
                corsConfiguration.setAllowedMethods(List.of("*"));
                corsConfiguration.setAllowedHeaders(List.of(SecurityConstants.JWT_HEADER));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setMaxAge(3600L);

                return corsConfiguration;

            }

        }).and()
        .csrf((csrf) -> csrf.csrfTokenRequestHandler(csrfRequestHandler)
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
        .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
        .authorizeHttpRequests()
        .requestMatchers("api/v1/candidatos/current-votacion" , "api/v1/votos/vote-by**").hasAnyAuthority("APRENDIZ")
        .requestMatchers("api/v1/aprendices/**", "api/v1/candidatos/**", "api/v1/votaciones/**").hasAnyAuthority("ADMINISTRADOR")
        .anyRequest().authenticated()
        .and()
        .formLogin().and().httpBasic();

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
