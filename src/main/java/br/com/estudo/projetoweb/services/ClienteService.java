package br.com.estudo.projetoweb.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.estudo.projetoweb.domain.Cidade;
import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.domain.Endereco;
import br.com.estudo.projetoweb.domain.enums.EnumPerfil;
import br.com.estudo.projetoweb.domain.enums.EnumTipoCliente;
import br.com.estudo.projetoweb.dto.ClienteDTO;
import br.com.estudo.projetoweb.dto.ClienteNewDTO;
import br.com.estudo.projetoweb.repositories.ClienteRepository;
import br.com.estudo.projetoweb.security.UserSpringSecurity;
import br.com.estudo.projetoweb.services.exception.AuthorizationException;
import br.com.estudo.projetoweb.services.exception.DataIntegratydException;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefixImgCliente;
	
	@Value("${img.profile.size}")
	private int prefixImageSize;

	public Cliente findById(Long id) {
		verificaSeUsuarioPossuiPermissao(id);
		Optional<Cliente> optionalCliente = clienteRepository.findById(id);

		return optionalCliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! ID: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	private void verificaSeUsuarioPossuiPermissao(Long id) {
		UserSpringSecurity userSpringSecurity = UserService.usuarioLogado();
		if (userSpringSecurity == null
				|| !userSpringSecurity.hasPermissao(EnumPerfil.ADMIN) && !id.equals(userSpringSecurity.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Cliente findByEmail(String email) {
		UserSpringSecurity userSpringSecurity = UserService.usuarioLogado();
		if (userSpringSecurity == null || userSpringSecurity.hasPermissao(EnumPerfil.ADMIN) || !email.equals(userSpringSecurity.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + userSpringSecurity.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return cliente;
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

	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cliente cliente = new Cliente(clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(),
				EnumTipoCliente.toEnum(clienteNewDTO.getTipo()),
				bCryptPasswordEncoder.encode(clienteNewDTO.getSenha()));
		Cidade cidade = new Cidade(clienteNewDTO.getCidadeId());
		Endereco endereco = new Endereco(clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(),
				clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);
		cliente.setEnderecos(new ArrayList<>());
		cliente.getEnderecos().add(endereco);
		cliente.setTelefones(new HashSet<>());
		cliente.getTelefones().add(clienteNewDTO.getTelefone());
		if (clienteNewDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		if (clienteNewDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		return cliente;
	}

	private void updateData(Cliente cliente1, Cliente cliente2) {
		cliente1.setNome(cliente2.getNome());
		cliente1.setEmail(cliente2.getEmail());
	}

	public URI uploadFotoPerfil(MultipartFile multipartFile) {
		UserSpringSecurity userSpringSecurity = UserService.usuarioLogado();
		if (userSpringSecurity == null) {
			throw new AuthorizationException("Acesso negado");
		}
		BufferedImage imagem = imageService.getJpgImagemFromFile(multipartFile);
		imagem = imageService.recortaImagem(imagem);
		imagem = imageService.redimensionaImagem(imagem, prefixImageSize);
		String fileName = prefixImgCliente + userSpringSecurity.getId() + ".jpg";
		return s3Service.uploadFile(imageService.getInputStream(imagem, "jpg"), fileName, "image");
	}
}
