package br.com.estudo.projetoweb.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.estudo.projetoweb.security.UserSpringSecurity;

public class UserService {

	/* O metodo abaixo retorna o usuário que está logado no sistema */
	public static UserSpringSecurity usuarioLogado() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
