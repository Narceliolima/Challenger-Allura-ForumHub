package br.com.example.ForumHub.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SprintDocConfigurations {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("bearer-key",
								new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.info(new Info()
                        .title("ForumHub API")
                        .description("API Rest da aplicação ForumHub, contendo as funcionalidades de CRUD de topicos.")
                        .contact(new Contact()
                                .name("Time Backend")
                                .email("backend@forumhuballura.com.br"))
                 .license(new License()
                         .name("Apache 2.0")
                         .url("http://forumhub/api/licenca")));
	}
}
