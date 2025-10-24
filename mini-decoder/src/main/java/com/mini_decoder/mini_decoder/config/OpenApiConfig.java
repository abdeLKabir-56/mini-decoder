package com.mini_decoder.mini_decoder.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private final ServletContext context;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mini Decoder API")
                        .version("1.0.0")
                        .description("API documentation for the Mini Decoder project, which handles sensor data ingestion and forecasting."))
                .addServersItem(new Server().url(context.getContextPath()));
    }
}
