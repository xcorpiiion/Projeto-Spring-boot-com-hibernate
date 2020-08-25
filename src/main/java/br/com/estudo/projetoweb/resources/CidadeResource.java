package br.com.estudo.projetoweb.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estudo.projetoweb.domain.Cidade;
import br.com.estudo.projetoweb.dto.CidadeDTO;
import br.com.estudo.projetoweb.services.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping(value = "/{estadoId}")
	public ResponseEntity<List<CidadeDTO>> find(@PathVariable Long estadoId) {
		List<Cidade> cidades = cidadeService.findByCidade(estadoId);
		List<CidadeDTO> cidadesDTO = cidades.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList());
		return ResponseEntity.ok().body(cidadesDTO);
	}

}
