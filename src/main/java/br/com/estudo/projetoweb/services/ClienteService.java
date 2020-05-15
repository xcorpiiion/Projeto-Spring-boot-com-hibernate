package br.com.estudo.projetoweb.services;

import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.repositories.ClienteRepository;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository ClienteRepository;

    public Cliente buscar(Long id){
        Optional<Cliente> optionalCliente = ClienteRepository.findById(id);

        return optionalCliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrato! ID: " + id
        + ", Tipo: " + Cliente.class.getName()));
    }
}
