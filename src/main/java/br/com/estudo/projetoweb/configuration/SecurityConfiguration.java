package br.com.estudo.projetoweb.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.estudo.projetoweb.security.JwtAuthenticationFilter;
import br.com.estudo.projetoweb.security.JwtAutorizationFilter;
import br.com.estudo.projetoweb.security.JwtUtil;
import br.com.estudo.projetoweb.services.UserDetailService;

@Configuration
@EnableWebSecurity
/*
 * Essa anotação vai permitir com que eu coloque anotações de pré-autorização
 * nos endpoints
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/*
	 * Essa variavel são o que vai está liberado para ser acessado pois o resto o
	 * Token vai bloquear
	 */
	private static String[] LIBERADOS_PELO_TOKEN = { "/h2-console/**" };
	private static String[] LIBERADOS_PELO_TOKEN_APENAS_RETORNA_VALORES_ACESSO_PUBLICO = { "/produtos/**",
			"/categorias/**", "/estados**", "/cidades/**" };
	private static String[] LIBERADOS_PELO_TOKEN_QUEM_PODE_INSERIR_ACESSO_PRIVADO = { "/clientes/**",
			"/authorization/forgotPassword/**" };

	@Autowired
	private Environment enviroment;

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	/*
	 * Esse método cuida da parte de autorização do usuario. por exemplo, ele pega o
	 * usuario que foi autentificado pelo AuthenticationManagerBuilder e verifica se
	 * ele possui autorização para acessar determinada página.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		verificaSePossuiProfileAtivo(http);

		/*
		 * Esse metodo abaixo desabilita a proteção de ataque csrf em sistemas
		 * stateless, pois esse ataque é baseado no armazenamento da autentificação em
		 * sessão
		 */
		http.cors().and().csrf().disable();
		/*
		 * authorizaRequests() -> ele requisita uma autorização. antMatchers() -> ele
		 * pede os MATCHERS que são os caminhos que estão na variavel (Ou seja, quem
		 * pode acessar o que). LIBERADOS_PELO_TOKEN permitAll() -> ele permite que
		 * todos os antMatchers passados como argumento sejam permitidos para serem
		 * acessados . anyRequest()-> ele fala que todas as outras request devem ser
		 * autentificadas por causa do metodo "authenticated()".
		 */

		/*
		 * hasRole() -> ele pede o perfil, ou seja, quem pode acessar a url. Nesse caso
		 * quem possui o perfil de Usuario
		 */
		/*
		 * hasAnyRole() -> ele é identica ao hasRole() mas ele pode receber mais de um
		 * perfil.
		 */
		/*
		 * http.csrf().disable().authorizeRequests().antMatchers(LIBERADOS_PELO_TOKEN).
		 * hasRole("USER");
		 */

		/*
		 * and() -> ele faz o meu metodo voltar para o inicio, ou seja, ele seria lido
		 * novamente pelo http.csrf().disable() até o final
		 */
		/* formLogin("/login") -> ele manda para uma tela de login */
		/*
		 * http.csrf().disable().authorizeRequests().antMatchers(LIBERADOS_PELO_TOKEN).
		 * and.formLogin("/login");
		 */

		http.authorizeRequests().antMatchers(HttpMethod.GET, LIBERADOS_PELO_TOKEN_APENAS_RETORNA_VALORES_ACESSO_PUBLICO)
				.permitAll().antMatchers(LIBERADOS_PELO_TOKEN).permitAll()
				.antMatchers(LIBERADOS_PELO_TOKEN_QUEM_PODE_INSERIR_ACESSO_PRIVADO).permitAll().anyRequest()
				.authenticated();
		/* O método a baixo que fará todo o filtro de autentificação do usuário */
		http.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil));
		/* O método a baixo que fará todo o filtro de autorização do usuário */
		http.addFilter(new JwtAutorizationFilter(authenticationManager(), jwtUtil, userDetailService));
		/*
		 * Essa configuração garante que o backend não irá criar uma sessão para o
		 * usuario, ele sempre irá precisar do token para acessar
		 */
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	private void verificaSePossuiProfileAtivo(HttpSecurity http) throws Exception {
		if (Arrays.asList(enviroment.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
	}

	/* Esse metodo abaixo serve para criptografar uma senha */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * Esse método vai trzer os objetos que irão fazer as configurações do usuario e
	 * adicionar esses usuarios dentro do contexto do Security Em resumo ele vai
	 * cuidar da autentificação dos usuarios.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		/*
		 * userDetailsService() -> ele carrega os usuarios que vem do UserDetailsService
		 */
		/* passwordEncoder -> ele vai comparar a senha do usuario */
		authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder());
		super.configure(authenticationManagerBuilder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		/* O meu Security vai ignorar essas urls que são do swagger */
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/uri", "/swagger-resources/**",
				"/cofiguration/security", "/swagger-ui.html", "/webjars/**");
	}

}
