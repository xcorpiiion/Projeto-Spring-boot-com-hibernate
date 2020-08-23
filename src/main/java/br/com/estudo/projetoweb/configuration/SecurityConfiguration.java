package br.com.estudo.projetoweb.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/*
	 * Essa variavel são o que vai está liberado para ser acessado pois o resto o
	 * Token vai bloquear
	 */
	private static String[] LIBERADOS_PELO_TOKEN = { "/h2-console/**" };
	private static String[] LIBERADOS_PELO_TOKEN_APENAS_RETORNA_VALORES = { "/produtos/**", "/categorias/**" };
	
	@Autowired
	private Environment enviroment;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		verificaSePossuiProfileAtivo(http);
		
		/*Esse metodo abaixo desabilita a proteção de ataque csrf em sistemas stateless, pois esse ataque é baseado no armazenamento 
		 * da autentificação em sessão*/
		http.cors().and().csrf().disable();
		/*
		 * authorizaRequests() -> ele requisita uma autorização.
		 * antMatchers() -> ele pede os MATCHERS que são os caminhos que estão na variavel.
		 * LIBERADOS_PELO_TOKEN 
		 * permitAll() -> ele permite que todos os antMatchers passados como argumento sejam permitidos para serem acessados .
		 * anyRequest() -> ele fala que todas as outras request devem ser autentificadas por causa do metodo "authenticated()".
		 */
		http.authorizeRequests().antMatchers(HttpMethod.GET, LIBERADOS_PELO_TOKEN_APENAS_RETORNA_VALORES).permitAll()
		.antMatchers(LIBERADOS_PELO_TOKEN).permitAll().anyRequest().authenticated();
		/*Essa configuração garante que o backend não irá criar uma sessão para o usuario*/
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	private void verificaSePossuiProfileAtivo(HttpSecurity http) throws Exception {
		if(Arrays.asList(enviroment.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
	}
	
	/*
	 * Esse metodo abaixo permite com que eu possa acessar os meus ends points com
	 * multiplas fontes com as configurações básicas.
	 */
	@Bean
	protected CorsConfigurationSource corsConfiguration() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	/*Esse metodo abaixo serve para criptografar uma senha*/
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
