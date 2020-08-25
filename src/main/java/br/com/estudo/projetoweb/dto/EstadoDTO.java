package br.com.estudo.projetoweb.dto;

import java.io.Serializable;

import br.com.estudo.projetoweb.domain.Estado;

public class EstadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;

	public EstadoDTO() {
		super();
	}
	
	public EstadoDTO(Estado estado) {
		this.id = estado.getId();
		this.nome = estado.getNome();
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
