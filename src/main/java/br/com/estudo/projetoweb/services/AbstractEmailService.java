package br.com.estudo.projetoweb.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	/*Processa o html que está no email/confirmacaoPedido e me retorna ele em formato string*/
	protected String htmlFromTemplatePedido(Pedido pedido) {
		Context context = new Context();
		/*Esse metodo dá o apelido para a variavel que está no html email/confirmacaoPedido*/
		context.setVariable("pedido", pedido);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		try {
		MimeMessage mimeMailMessage = prepareMimeMessageFromPedido(pedido);
		sendHtmlEmail(mimeMailMessage);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		}
	}
	
	@Override
	public void sendNewPassowrodToEmail(Cliente cliente, String novaSenha) {
		SimpleMailMessage simpleMailMessage = prepareNewPasswordEmail(cliente, novaSenha);
		sendEmail(simpleMailMessage);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String novaSenha) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		/*Para quem envia o email*/
		simpleMailMessage.setTo(cliente.getEmail());
		/*Quem recebe o email*/
		simpleMailMessage.setFrom(sender);
		/*Assunto do email*/
		simpleMailMessage.setSubject("Solicitação de nova senha: ");
		/*Data do envio*/
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		/*Corpo do email*/
		simpleMailMessage.setText("Nova senha: " + novaSenha);
		return simpleMailMessage;
	}

	private MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		/*Para quem envia o email*/
		mimeMessageHelper.setTo(pedido.getCliente().getEmail());
		/*Quem recebe o email*/
		mimeMessageHelper.setFrom(sender);
		/*Assunto do email*/
		mimeMessageHelper.setSubject("Pedido confirmado! Código: " + pedido.getId());
		/*Data do envio*/
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		/*Corpo do email*/
		mimeMessageHelper.setText(htmlFromTemplatePedido(pedido), true);
		return mimeMessage;
	}
	
}
