package br.com.estudo.projetoweb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estudo.projetoweb.domain.Cidade;
import br.com.estudo.projetoweb.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> findByCidade(Long estadoId){
		return cidadeRepository.findByCidades(estadoId);
	}
	
}
