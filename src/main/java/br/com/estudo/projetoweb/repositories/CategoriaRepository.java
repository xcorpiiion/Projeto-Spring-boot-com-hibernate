package br.com.estudo.projetoweb.repositories;

import br.com.estudo.projetoweb.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
