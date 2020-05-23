package br.com.estudo.projetoweb.dto;

import br.com.estudo.projetoweb.domain.Categoria;

import java.io.Serializable;

public class CategoriaDTO implements Serializable {

    private Long id;

    private String nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
