package br.com.estudo.projetoweb.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.estudo.projetoweb.domain.Categoria;
import br.com.estudo.projetoweb.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Transactional(readOnly = true)
	/* Essa anotação serve para eu fazer uma busca personalizada no banco de dados */
	/* produto.nome like %:nome% ele busca uma string que tenha o mesmo nome tanto no começo quanto no final */
	/* categoria IN :categorias ele verifica se possui a categoria dentro da categoria que vai como argumento*/
	/* :nome e :categoria é o argumento que é passado como parametro atráves de uma anotação*/
	/* @Param("nome do argumento") -> essa anotação serve para passarmos o nosso argumento para a anotação @Query (que no caso são os valores que tem o :
	 * exemplo :nome é o mesmo nome que vai no @Param("nome"))*/
	@Query("SELECT DISTINCT produto FROM Produto produto INNER JOIN produto.categorias categoria WHERE produto.nome like %:nome% AND categoria IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

}
