package br.com.estudo.projetoweb.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.dto.ClienteDTO;
import br.com.estudo.projetoweb.dto.ClienteNewDTO;
import br.com.estudo.projetoweb.services.ClienteService;

/*@RestController -> É responsável por criar um Map do
Model Object e encontrar uma View. Basicamente ele é o
responsável por controlar as requisições indicando quem
deve receber as requisições para quem deve responde-las.
Também pode mandar diretamente no fluxo
do response usando a anotação e
concluir a solicitação.*/
@RestController
/* Essa anotação serve para definir o caminho de uma url */
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	/*
	 * @PathVariable -> é utilizado quando o valor da variável é passada diretamente
	 * na URL, mas não como um parametro que você passa após o sinal de interrogação
	 * (?) mas sim quando o valor faz parte da url.
	 */
	/*
	 * Dentro desse @RequestMapping (O Get faz parte dessa anotação) ele possui
	 * alguns valores. 
	 * Value -> ele recebe uma lista de valores strings que serve
	 * para mostrar o caminho da url para chegar nele. 
	 * Consumes -> ele recebe o tipo
	 * de dado que eu posso enviar como por exemplo um "application/json" ou um
	 * "application/xml" (No caso eu poderia fazer isso com o metodo POST). 
	 * Produces -> ele segue a mesma logica do COnsumes mas a diferença é que ele fala o tipo
	 * de dado que vai ser retornado.
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		Cliente cliente = clienteService.findById(id);
		/*
		 * Quando eu uso esse metodo "ok()" eu estou dizendo que o código é 200, ou
		 * seja, ele achou com sucesso
		 */
		return ResponseEntity.ok().body(cliente);
	}

	/*
	 * ResponseEntity -> ele representa toda a resposta HTTP: código de status,
	 * cabeçalhos e corpo. O ResponseEntity é um tipo genérico. Como resultado,
	 * podemos usar qualquer tipo como corpo de resposta (Como String, Objetos e
	 * etc...)
	 */
	@GetMapping(value = "/email")
	public ResponseEntity<?> find(@RequestParam(value = "value") String email) {
		Cliente cliente = clienteService.findByEmail(email);
		return ResponseEntity.ok().body(cliente);
	}

	/* @ResquestBody -> Ele converte o Json em um Objeto java automaticamente */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> upadate(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Long id) {
		Cliente cliente = clienteService.fromDTO(clienteDTO);
		cliente.setId(id);
		clienteService.update(cliente);
		return ResponseEntity.noContent().build();
	}

	/* @ResquestBody -> Ele converte o Json em um Objeto java automaticamente */
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
		Cliente cliente = clienteService.fromDTO(clienteNewDTO);
		clienteService.insert(cliente);
		/*
		 * O metodo que a URI esta recebendo faz o seguinte: Ele recebe um componente
		 * criado a partir da atual requisição que vem do caminho informado pelo
		 * “path(“”)” depois ele constrói e expande seu argumento e converte para uma
		 * URI.
		 */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();
		/*
		 * URIBuilder -> ele já pega automaticamente o endereço do servidor, e você só
		 * precisa passar o final da URI.
		 */
		return ResponseEntity.created(uri).build();
	}

	@PostMapping(value = "/picture")
	public ResponseEntity<Void> uploadFotoPerfil(@RequestParam MultipartFile multipartFile) {
		URI uri = clienteService.uploadFotoPerfil(multipartFile);
		return ResponseEntity.created(uri).build();
	}

	/*
	 * @PreAuthorize -> Essa anotação faz com que apenas quem seja admin possa
	 * acessar esse endpoint essa anotação só é possivel graças a
	 * anotação @EnableGlobalMethodSecurity(prePostEnabled = true) que está na
	 * classe SecurityConfiguration
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clienteService.delete(id);
		/*Ele retorna o noContent() quando a gente só quer retornar o codigo da requisição*/
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> clientes = clienteService.findAll();
		List<ClienteDTO> clienteDTOS = clientes.stream().map(cliente -> addClienteInClienteDTO(cliente))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(clienteDTOS);
	}

	private ClienteDTO addClienteInClienteDTO(Cliente cliente) {
		return new ClienteDTO(cliente);
	}

	/*
	 * @RequestParam -> ele permite passar valores pelo parametro para a url mas
	 * esse valor não pertence a url, por exemplo quando eu passo o número da
	 * pagina, esse valor não pertence a url mas ele está funcionando como uma
	 * especie de filtro. Esse valor é capturado pelo controller através dos metodos
	 * GET ou POST
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> clientes = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> clienteDTOS = clientes.map(cliente -> addClienteInClienteDTO(cliente));
		return ResponseEntity.ok().body(clienteDTOS);
	}

}
