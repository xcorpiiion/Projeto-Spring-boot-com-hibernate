package br.com.estudo.projetoweb.resources;

import br.com.estudo.projetoweb.domain.Categoria;
import br.com.estudo.projetoweb.dto.CategoriaDTO;
import br.com.estudo.projetoweb.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok().body(categoria);
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaService.fromDTO(categoriaDTO);
        categoria = categoriaService.insert(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoriaDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> upadate(@RequestBody Categoria categoria, @PathVariable Long id) {
        categoria.setId(id);
        categoriaService.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> categorias = categoriaService.findAll();
        List<CategoriaDTO> categoriaDTOS = categorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoriaDTOS);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
                                                       @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
                                                       @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Categoria> categorias = categoriaService.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> categoriaDTOS = categorias.map(categoria -> new CategoriaDTO(categoria));
        return ResponseEntity.ok().body(categoriaDTOS);
    }

}
