package br.com.estudo.projetoweb.resources;

import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService ClienteService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Cliente Cliente = ClienteService.buscar(id);
        return ResponseEntity.ok().body(Cliente);
    }

}
