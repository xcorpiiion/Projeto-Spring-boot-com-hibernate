package br.com.estudo.projetoweb.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;

	@Override
	public void sendEmail(SimpleMailMessage simpleMailMessage) {
		LOGGER.info("Simulando envio do email...");
		mailSender.send(simpleMailMessage);
		LOGGER.info("Email enviado");
	}

}
