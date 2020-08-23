package br.com.estudo.projetoweb.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.estudo.projetoweb.services.UserDetailService;

public class JwtAutorizationFilter extends BasicAuthenticationFilter {

	private JwtUtil jwtUtil;

	/* Ele será utilizado para verificar se o Token é valido */
	private UserDetailService userDetailService;

	public JwtAutorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
			UserDetailService userDetailService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailService = userDetailService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/*
		 * Ele pega o valor Authorization que está no cabeçalho (que é o mesmo declarado
		 * no successfulAuthentication() da classe JwtAuthenticationFilter) dentro do
		 * método tem um campo -> response.addHeader("Authorization", "Bearer" + token)
		 * esse campo está criando o cabeçalhio que será enviado quando a gente tentar
		 * fazer uma request para fazer o login ("Com o POST do Postman por exemplo") e
		 * ele na parte de Header vai ter um Authorization na parte da Key do Postman,
		 * nessa key terar o valor "Authorization" e é justamente esse valor que o campo
		 * a baixo vai obter e ele vai obter a string do campo value.
		 * 
		 */
		String header = request.getHeader("Authorization");
		/*
		 * Esse if verifica se o meu Value no header começa com Bearer (que foi o valor
		 * inicial que eu coloquei no response.addHeader("Authorization", "Bearer" +
		 * token))
		 */
		if (header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(header.substring(7));
			if (authenticationToken != null) {
				/*
				 * O campo abaixo vai liberar o acesso ao meu usuário caso o Token seja válidado
				 */
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		/* O campo abaixo faz com que a requisição continue normalmente */
		chain.doFilter(request, response);
	}

	/*
	 * Esse método pega o valor que está na frente do Bearer (que é o Token em sí) e
	 * ele vai me retorna o UsernamePasswordAuthenticationToken
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (jwtUtil.isTokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			/*Esse campo a baixo vai me retornar um usuário*/
			UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		}
		return null;
	}

}
