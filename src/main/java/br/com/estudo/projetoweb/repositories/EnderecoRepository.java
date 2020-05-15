package br.com.estudo.projetoweb.repositories;

import br.com.estudo.projetoweb.Endereco;
import br.com.estudo.projetoweb.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
