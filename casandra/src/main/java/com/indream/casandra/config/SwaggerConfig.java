package com.indream.casandra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	@Bean
	public Docket getSwagger() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.any()).build();

	}
}
