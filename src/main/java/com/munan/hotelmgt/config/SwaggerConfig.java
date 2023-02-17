package com.munan.hotelmgt.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Hotel Management API", version = "1.0", description = "Hotel Management API"))
@Configuration
public class SwaggerConfig {
}
