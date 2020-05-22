package br.com.estudo.projetoweb.repositories;

import br.com.estudo.projetoweb.domain.Categoria;
import br.com.estudo.projetoweb.domain.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

}
