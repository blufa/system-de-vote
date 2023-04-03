package com.senatic.servervotingsystem.configuration.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition
@Configuration
public class VotingSysDocs {

  @Bean
    OpenAPI baseOpenAPI(){
      return new OpenAPI().info(new Info().title("API-voting-system Docs").description("APIs documentation for voting system server App"));   
    }
    
}
