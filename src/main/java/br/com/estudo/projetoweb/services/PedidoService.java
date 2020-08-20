package br.com.estudo.projetoweb.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estudo.projetoweb.domain.Pedido;
import br.com.estudo.projetoweb.repositories.IPedidoRepository;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

    @Autowired
    private IPedidoRepository pedidoRepository;

    public Pedido buscar(Long id){
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);

        return optionalPedido.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrato! ID: " + id
        + ", Tipo: " + Pedido.class.getName()));
    }
}
