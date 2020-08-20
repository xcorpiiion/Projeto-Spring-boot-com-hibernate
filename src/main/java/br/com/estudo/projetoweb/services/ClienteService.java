package br.com.estudo.projetoweb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.dto.ClienteDTO;
import br.com.estudo.projetoweb.repositories.ClienteRepository;
import br.com.estudo.projetoweb.services.exception.DataIntegratydException;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente findById(Long id){
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        return optionalCliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrato! ID: " + id
        + ", Tipo: " + Cliente.class.getName()));
    }
    
    public List<Cliente> findAll() {
    	return clienteRepository.findAll();
    }
    
    public Cliente update(Cliente categoria) {
        findById(categoria.getId());
        return clienteRepository.save(categoria);
    }

    public void delete(Long id) {
        findById(id);
        try {
            clienteRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegratydException("Não é possivel excluir categoria que possui produtos");
        }
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail());
    }
}
