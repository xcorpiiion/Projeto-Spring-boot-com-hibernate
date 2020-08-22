package br.com.estudo.projetoweb.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(SimpleMailMessage simpleMailMessage) {
		LOGGER.info("Enviando email...");
		mailSender.send(simpleMailMessage);
		LOGGER.info("Email enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage simpleMailMessage) {
		LOGGER.info("Simulando envio do email...");
		javaMailSender.send(simpleMailMessage);
		LOGGER.info("Email enviado");
	}

}
