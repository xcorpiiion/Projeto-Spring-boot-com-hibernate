package br.com.estudo.projetoweb.services;

import br.com.estudo.projetoweb.domain.Categoria;
import br.com.estudo.projetoweb.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria buscar(Long id){
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        return optionalCategoria.orElse(null);
    }
}
