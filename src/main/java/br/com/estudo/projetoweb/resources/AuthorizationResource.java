package br.com.estudo.projetoweb.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estudo.projetoweb.dto.EmailDTO;
import br.com.estudo.projetoweb.security.JwtUtil;
import br.com.estudo.projetoweb.security.UserSpringSecurity;
import br.com.estudo.projetoweb.services.AuthorizationService;
import br.com.estudo.projetoweb.services.UserService;

@RestController
@RequestMapping(value = "/authorization")
public class AuthorizationResource {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthorizationService authorizationService;

	/*
	 * Método abaixo gera um novo token quando o usuário estiver logado e o tempo do
	 * token estiver acabando
	 */
	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity userSpringSecurity = UserService.usuarioLogado();
		String token = jwtUtil.generateToken(userSpringSecurity.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		/*
		 * Esse comanda faz com que o nosso cabeçalho fique exposto pois naturalmente
		 * ele não fica, então eu preciso desse comando para ele ficar exposto
		 */
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/forgotPassword")
	public ResponseEntity<Void> forgotPassword(@Valid @RequestBody EmailDTO emailDTO) {
		authorizationService.enviaUmaNovaSenha(emailDTO.getEmail());
		return ResponseEntity.noContent().build();
	}

}
