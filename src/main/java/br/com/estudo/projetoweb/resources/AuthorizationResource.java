package br.com.estudo.projetoweb.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estudo.projetoweb.security.JwtUtil;
import br.com.estudo.projetoweb.security.UserSpringSecurity;
import br.com.estudo.projetoweb.services.UserService;

@RestController
@RequestMapping(value = "/authorization")
public class AuthorizationResource {

	@Autowired
	private JwtUtil jwtUtil;

	/*
	 * Método abaixo gera um novo token quando o usuário estiver logado e o tempo do
	 * token estiver acabando
	 */
	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity userSpringSecurity = UserService.usuarioLogado();
		String token = jwtUtil.generateToken(userSpringSecurity.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}

}
