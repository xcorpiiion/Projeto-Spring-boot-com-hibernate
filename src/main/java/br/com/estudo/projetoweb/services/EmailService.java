package br.com.estudo.projetoweb.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.estudo.projetoweb.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage simpleMailMessage);

}
