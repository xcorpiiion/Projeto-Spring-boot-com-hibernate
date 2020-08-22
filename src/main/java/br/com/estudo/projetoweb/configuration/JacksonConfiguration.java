package br.com.estudo.projetoweb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.estudo.projetoweb.domain.PagamentoBoleto;
import br.com.estudo.projetoweb.domain.PagamentoCartao;

@Configuration
public class JacksonConfiguration {

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		return converteClasseParaJson();
	}

	private Jackson2ObjectMapperBuilder converteClasseParaJson() {
		return new Jackson2ObjectMapperBuilder() {
			@Override
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoBoleto.class);
				objectMapper.registerSubtypes(PagamentoCartao.class);
				super.configure(objectMapper);
			}
		};
	}
}
