package br.com.estudo.projetoweb.services;

import br.com.estudo.projetoweb.domain.Categoria;
import br.com.estudo.projetoweb.repositories.CategoriaRepository;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findById(Long id) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);

        return optionalCategoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrato! ID: " + id
                + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria categoria) {
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        findById(categoria.getId());
        return categoriaRepository.save(categoria);
    }
}
