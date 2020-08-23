package br.com.estudo.projetoweb.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.repositories.ClienteRepository;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;

@Service
public class AuthorizationService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private EmailService emailService;

	private Random random;

	public void enviaUmaNovaSenha(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}

		String novaSenha = novaSenha();
		cliente.setSenha(bCryptPasswordEncoder.encode(novaSenha));
		clienteRepository.save(cliente);
		emailService.sendNewPassowrodToEmail(cliente, novaSenha);
	}

	private String novaSenha() {
		char[] senha = new char[10];
		for (int i = 0; i < 10; i++) {
			senha[i] = geradorSenha();
		}
		return new String(senha);
	}

	private char geradorSenha() {
		random = new Random();
		int geraNumero = random.nextInt(3);
		switch (geraNumero) {
		case 0:
			/*Gera um número*/
			return (char) (random.nextInt(10) + 48);
		case 1:
			/*Gera uma letra maiuscula*/
			return (char) (random.nextInt(26) + 65);
		case 2:
			/*Gera uma letra minuscula*/
			return (char) (random.nextInt(26) + 97);
		default:
			break;
		}
		return 0;
	}

}
