package br.com.estudo.projetoweb.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket docket() {
		/*
		 * useDefaultResponseMessages(boolean) -> ele define algumas mensagens padrões
		 * como por exemplo 200 ok, 400 Forbbiden
		 */
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.estudo.projetoweb.resources"))
				.paths(PathSelectors.any()).build().securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey())).apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Vendas API").description("Projeto de vendas").version("1.0")
				.contact(contact()).build();
	}

	/* Esse é o contato que irá aparecer na documentação */
	private Contact contact() {
		return new Contact("vini cruz", "https://github.com/xcorpiiion/Projeto-Spring-boot-com-hibernate",
				"vinicius.da.silva.cruz@gmail.com");
	}

	public ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defultAuthorization()).forPaths(PathSelectors.any())
				.build();
	}

	public List<SecurityReference> defultAuthorization() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "acessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		SecurityReference securityReference = new SecurityReference("JWR", authorizationScopes);
		List<SecurityReference> securityReferences = new ArrayList<>();
		securityReferences.add(securityReference);
		return securityReferences;
	}
}
