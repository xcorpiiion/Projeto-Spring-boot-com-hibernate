package br.com.estudo.projetoweb.resources;

import br.com.estudo.projetoweb.domain.Categoria;
import br.com.estudo.projetoweb.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscar(id);
        return ResponseEntity.ok().body(categoria);
    }

}
