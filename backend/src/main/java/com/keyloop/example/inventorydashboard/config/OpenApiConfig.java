package com.keyloop.example.inventorydashboard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("The Intelligent Inventory Dashboard")
                        .version("1.0.0")
                        .description("This is the interactive API documentation for my backend project. Use this test harness to interact with the endpoints."));
    }
}