package com.senatic.servervotingsystem.configuration.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;

@OpenAPIDefinition
@Configuration
public class VotingSysDocs {
  @Bean
    OpenAPI baseOpenAPI(){

        return new OpenAPI();
        
    }
}
