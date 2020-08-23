package br.com.estudo.projetoweb.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.estudo.projetoweb.domain.Pedido;
import br.com.estudo.projetoweb.dto.PedidoDTO;
import br.com.estudo.projetoweb.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscar(id);
        return ResponseEntity.ok().body(pedido);
    }
    
    @PostMapping()
    public ResponseEntity<Void> insert(@Valid @RequestBody PedidoDTO pedidoDTO) {
    	Pedido pedido = pedidoService.fromDTO(pedidoDTO);
        pedido = pedidoService.insert(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    @GetMapping()
	public ResponseEntity<Page<PedidoDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "instant") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		Page<Pedido> pedidos = pedidoService.findPage(page, linesPerPage, orderBy, direction);
		Page<PedidoDTO> pedidoDTOS = pedidos.map(pedido -> new PedidoDTO(pedido));
		return ResponseEntity.ok().body(pedidoDTOS);
	}

}
