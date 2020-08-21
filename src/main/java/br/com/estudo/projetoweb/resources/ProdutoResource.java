package br.com.estudo.projetoweb.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.estudo.projetoweb.domain.Produto;
import br.com.estudo.projetoweb.dto.ProdutoDTO;
import br.com.estudo.projetoweb.resources.utils.URL;
import br.com.estudo.projetoweb.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		Produto produto = produtoService.buscar(id);
		return ResponseEntity.ok().body(produto);
	}

	@GetMapping()
	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categoria", defaultValue = "") String categoria,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String nomeDecoded = URL.decodificaParam(nome);
		List<Long> ids = URL.convertStringInLongList(categoria);
		Page<Produto> produtos = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> produtosDTOS = produtos.map(produto -> new ProdutoDTO(produto));
		return ResponseEntity.ok().body(produtosDTOS);
	}

}
