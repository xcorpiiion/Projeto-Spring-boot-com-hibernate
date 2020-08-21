package br.com.estudo.projetoweb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.estudo.projetoweb.domain.Categoria;
import br.com.estudo.projetoweb.domain.Produto;
import br.com.estudo.projetoweb.repositories.CategoriaRepository;
import br.com.estudo.projetoweb.repositories.ProdutoRepository;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto buscar(Long id) {
		Optional<Produto> optionalProduto = produtoRepository.findById(id);

		return optionalProduto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! ID: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Long> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return produtoRepository.search(nome, categorias, pageRequest);

	}
}
