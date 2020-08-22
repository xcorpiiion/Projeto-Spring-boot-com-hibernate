package br.com.estudo.projetoweb.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.estudo.projetoweb.domain.PagamentoBoleto;

@Service
public class BoletoService {
	
	public void preencherPagamentoBoleto(PagamentoBoleto boleto, Date instantePedido) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(instantePedido);
		calendar.add(Calendar.DAY_OF_MONTH,  7);
		boleto.setDataVencimento(calendar.getTime());
	}
	

}
