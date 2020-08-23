package br.com.estudo.projetoweb.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

}
