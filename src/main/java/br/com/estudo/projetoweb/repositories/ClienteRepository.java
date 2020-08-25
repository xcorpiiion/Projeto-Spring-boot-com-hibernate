package br.com.estudo.projetoweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.estudo.projetoweb.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	/*
	 * Uma transação garante que todo processo deva ser executado com êxito, é “tudo
	 * ou nada” (princípio da atomicidade). Quando você realiza algum procedimento
	 * bancário transações estão intimamente ligadas a todos os seus passos,
	 * garantindo que nenhuma informação seja persistida se todo o processo não
	 * tiver 100% de êxito.
	 */
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);

}
