package br.com.estudo.projetoweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estudo.projetoweb.domain.Pedido;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

}
