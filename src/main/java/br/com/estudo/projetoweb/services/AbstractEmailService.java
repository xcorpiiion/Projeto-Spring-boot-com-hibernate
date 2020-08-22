package br.com.estudo.projetoweb.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.estudo.projetoweb.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage simpleMailMessage = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(simpleMailMessage);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		/*Para quem envia o email*/
		simpleMailMessage.setTo(pedido.getCliente().getEmail());
		/*Quem recebe o email*/
		simpleMailMessage.setFrom(sender);
		/*Assunto do email*/
		simpleMailMessage.setSubject("Pedido confirmado! Código: " + pedido.getId());
		/*Data do envio*/
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		/*Corpo do email*/
		simpleMailMessage.setText(pedido.toString());
		return simpleMailMessage;
	}
	
}
