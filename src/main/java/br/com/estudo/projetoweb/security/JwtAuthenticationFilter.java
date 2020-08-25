package br.com.estudo.projetoweb.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.estudo.projetoweb.dto.CredencialDTO;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private JwtUtil jwtUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			/*
			 * Ele vai tentar pegar a requisição que está vindo do "request" (por exemplo
			 * quando eu dou um POST no Postman) e vai converter para a classe que vai como
			 * argumento (no caso CredencialDTO.class)
			 */
			CredencialDTO credencialDTO = new ObjectMapper().readValue(request.getInputStream(), CredencialDTO.class);
			/*
			 * Esse objeto abaixo vai receber os dados que foram obtidos do CredencialDTO
			 * junto com uma lista vazia
			 */
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					credencialDTO.getEmail(), credencialDTO.getSenha(), new ArrayList<>());
			/*
			 * authenticationManager.authenticate -> ele vai verificar se o usuario e senha
			 * são validos e vai retornar
			 */
			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/* Esse metodo será chamado caso a autentificação dê certo */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		/*
		 * authentication.getPrincipal() -> ele vai retornar um usuario do
		 * SpringSecurity por isso eu fiz um cast pois eu tenho uma classe que é do
		 * SpringSecurity e a partir dela eu pego o username do meu usuario
		 */
		String username = ((UserSpringSecurity) authentication.getPrincipal()).getUsername();
		/*
		 * Agora eu consigo gerar um token que é gerado da minha classe JwtUtil que já
		 * tem o código para a geração dele
		 */
		String token = jwtUtil.generateToken(username);
		/*
		 * Após a geração do Token eu consigo adicionar o mesmo como cabeçalho da
		 * requisição através do método addHeader(), onde ele pede o nome do cabeçalho e
		 * pede um valor (que no caso eu usei a palavra "Bearer" + o token que foi
		 * gerado)
		 */
		response.addHeader("Authorization", "Bearer " + token);
		/*
		 * Esse comanda faz com que o nosso cabeçalho fique exposto pois naturalmente
		 * ele não fica, então eu preciso desse comando para ele ficar exposto
		 */
		response.addHeader("access-control-expose-headers", "Authorization");
	}

	/* Essa classe é chamada quando o usuario não consegue autentificar */
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		/*
		 * O método a baixo será chamado caso ocorra alguem erro na autenficicação do
		 * usuario
		 */
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(respostaJsonPersonalizada());
		}

		private String respostaJsonPersonalizada() {
			long date = new Date().getTime();
			return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
					+ "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
		}
	}
}
