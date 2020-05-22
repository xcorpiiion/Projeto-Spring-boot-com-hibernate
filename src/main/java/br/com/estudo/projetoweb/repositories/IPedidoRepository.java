package br.com.estudo.projetoweb.repositories;

import br.com.estudo.projetoweb.domain.Estado;
import br.com.estudo.projetoweb.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

}
