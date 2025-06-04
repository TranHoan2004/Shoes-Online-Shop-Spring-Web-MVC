package com.HE180030.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OpenAPIConfig {
    @Value("${spring.config.openAPIPath}")
    String apiDocPath;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                        new Info().title("Shoes-Online shop")
                                .description("Documents for Shoes-Online shop application")
                                .version("1.0")
                                .contact(new Contact().name("API")) // thông tin liên hệ tới người đại diện
                                .license(new License().name("FU")) // giấy phép
                )
                .addServersItem(new Server()
                        .url(apiDocPath)
                        .description("Local Server")
                );
    }
}
