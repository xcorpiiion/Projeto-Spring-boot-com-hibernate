package br.com.estudo.projetoweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.estudo.projetoweb.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	@Transactional(readOnly = true)
	@Query("SELECT c FROM Cidade c WHERE c.estado.id = :estadoId ORDER BY c.nome")
	public List<Cidade> findByCidades(@Param("estadoId") Long estadoId);

}
