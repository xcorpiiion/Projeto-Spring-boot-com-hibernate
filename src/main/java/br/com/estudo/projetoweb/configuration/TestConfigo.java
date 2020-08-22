package br.com.estudo.projetoweb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.estudo.projetoweb.services.DatabaseService;

@Configuration
@Profile("test")
public class TestConfigo {
	
	@Autowired
	private DatabaseService databaseService;
	
	@Bean
	public boolean instanciaDatabase() {
		databaseService.instanciaTestDatabase();
		return true;
	}
	
}
