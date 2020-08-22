package br.com.estudo.projetoweb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.estudo.projetoweb.services.DatabaseService;
import br.com.estudo.projetoweb.services.EmailService;
import br.com.estudo.projetoweb.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfiguration {
	
	@Autowired
	private DatabaseService databaseService;
	
	@Bean
	public boolean instanciaDatabase() {
		databaseService.instanciaTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
