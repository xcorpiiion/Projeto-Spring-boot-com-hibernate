package br.com.estudo.projetoweb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.estudo.projetoweb.domain.Categoria;
import br.com.estudo.projetoweb.dto.CategoriaDTO;
import br.com.estudo.projetoweb.repositories.CategoriaRepository;
import br.com.estudo.projetoweb.services.exception.DataIntegratydException;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findById(Long id) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);

        return optionalCategoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrato! ID: " + id
                + ", Tipo: " + Categoria.class.getName()));
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria insert(Categoria categoria) {
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
    	updateData(findById(categoria.getId()), categoria);
        return categoriaRepository.save(categoria);
    }

    public void delete(Long id) {
        findById(id);
        try {
            categoriaRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegratydException("Não é possivel excluir categoria que possui produtos");
        }
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }
    
    private void updateData(Categoria categoria1, Categoria categoria2) {
    	categoria1.setNome(categoria2.getNome());
    }
}
