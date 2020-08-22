package br.com.estudo.projetoweb.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.estudo.projetoweb.services.DatabaseService;

@Configuration
@Profile("dev")
public class DevConfiguration {
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	/*A anotação acima serve para eu pegar o valor que está no application-dev.properties e armazeno dentro da variavel*/
	private String ddlHibernate;
	
	@Bean
	public boolean instanciaDatabase(DatabaseService databaseService) {
		/*Essa verificação faz com que eu possa criar ou não o banco de dados dependendo do valor que está na ddlHibernate
		 * pois o ddl hibernate pode ter outros valores*/
		if(!"create".equals(ddlHibernate)) {
			return false;
		}
		databaseService.instanciaTestDatabase();
		return true;
	}
	
}
