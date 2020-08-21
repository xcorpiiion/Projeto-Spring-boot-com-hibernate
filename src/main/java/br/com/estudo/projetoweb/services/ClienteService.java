package br.com.estudo.projetoweb.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.estudo.projetoweb.domain.Cidade;
import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.domain.Endereco;
import br.com.estudo.projetoweb.domain.enums.EnumTipoCliente;
import br.com.estudo.projetoweb.dto.ClienteDTO;
import br.com.estudo.projetoweb.dto.ClienteNewDTO;
import br.com.estudo.projetoweb.repositories.ClienteRepository;
import br.com.estudo.projetoweb.services.exception.DataIntegratydException;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente findById(Long id) {
		Optional<Cliente> optionalCliente = clienteRepository.findById(id);

		return optionalCliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! ID: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente update(Cliente cliente) {
		updateData(findById(cliente.getId()), cliente);
		return clienteRepository.save(cliente);
	}

	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		return clienteRepository.save(cliente);
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

	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail());
	}

	public Cliente fromDTO(ClienteNewDTO clienteNewDTO){
    	Cliente cliente = new Cliente(clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), EnumTipoCliente.toEnum(clienteNewDTO.getTipo()));
    	Cidade cidade = new Cidade(clienteNewDTO.getCidadeId());
		Endereco endereco = new Endereco(clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);
		cliente.setEnderecos(new ArrayList<>());
		cliente.getEnderecos().add(endereco);
		cliente.setTelefones(new HashSet<>());
    	cliente.getTelefones().add(clienteNewDTO.getTelefone());
    	if(clienteNewDTO.getTelefone2() != null) {
    		cliente.getTelefones().add(clienteNewDTO.getTelefone2());
    	}
    	if(clienteNewDTO.getTelefone3() != null) {
    		cliente.getTelefones().add(clienteNewDTO.getTelefone3());
    	}
		return cliente;
    }

	private void updateData(Cliente cliente1, Cliente cliente2) {
		cliente1.setNome(cliente2.getNome());
		cliente1.setEmail(cliente2.getEmail());
	}
}
