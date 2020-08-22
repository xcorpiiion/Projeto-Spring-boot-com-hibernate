package br.com.estudo.projetoweb.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage simpleMailMessage) {
		LOGGER.info("Simulando envio do email...");
		LOGGER.info(simpleMailMessage.toString());
		LOGGER.info("Email enviado");
	}

}
