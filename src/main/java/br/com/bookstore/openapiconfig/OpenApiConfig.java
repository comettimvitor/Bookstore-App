package br.com.bookstore.openapiconfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookstore() {
        return new OpenAPI()
                .info(new Info()
                        .title("API para cadastros de livros")
                        .description("Desafio técnico NT CONSULT. Criação de uma API REST para sistema de livraria."));
    }
}
