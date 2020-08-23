package br.com.estudo.projetoweb.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String username) {
		/* builder -> ele cria um Token */
		/* setJubject -> ele é o usuario que vai ser recebido */
		/* setExpiration -> ele é o tempo de expiração do Token */
		/*
		 * signWith -> ele serve para dizer como o Token vai ser assinado, ele pede um
		 * algoritmo de assinatura e o segredo
		 * "que foi definido no application.properties"
		 */
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + this.expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public boolean isTokenValido(String token) {
		/*
		 * Claims -> ele armazena as reinvendicações do Token, ou seja, a pessoa que
		 * está acessando o endpoint com o Token ela está reinvendicando algumas coisas
		 * (Que nesse caso é o usuário e o tempo de expiração) a pessoa que está
		 * acessando esse endpoint está alegando que ela é o usuário tal e que o tempo
		 * de expiração é tal
		 */
		Claims claims = getClaims(token);
		if (claims != null) {
			/*claims.getSubject() -> ele retorna o usuario*/
			String username = claims.getSubject();
			/*claims.getExpiration() -> ele pega a data de expiração*/
			Date expirationDate = claims.getExpiration();
			Date dataAtual = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && dataAtual.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	private Claims getClaims(String token) {
		try {
			/*o campo a baixo irá me retornar os claims a partir do Token*/
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			/*claims.getSubject() -> ele retorna o usuario*/
			return claims.getSubject();
		}
		return null;
	}

}
