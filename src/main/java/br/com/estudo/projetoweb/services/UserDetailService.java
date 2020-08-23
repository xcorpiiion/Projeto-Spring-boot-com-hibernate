package br.com.estudo.projetoweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.repositories.ClienteRepository;
import br.com.estudo.projetoweb.security.UserSpringSecurity;

@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	/*O metodo abaixo vai retornar um usuario*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Cliente cliente = clienteRepository.findByEmail(username);
		if(cliente == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserSpringSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}

}
