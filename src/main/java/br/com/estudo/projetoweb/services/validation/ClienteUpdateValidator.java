package br.com.estudo.projetoweb.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.dto.ClienteDTO;
import br.com.estudo.projetoweb.repositories.ClienteRepository;
import br.com.estudo.projetoweb.resources.exception.FieldMessage;

/*ConstraintValidator -> ele indica que esse meu campo será uma validação para a anotção.*/
/*Ele recebe dois parametros onde o primeiro é a anotação que eu acabei de criar e o segundo parametro é o tipo de dado que ele deve validar*/
public class ClienteUpdateValidator implements ConstraintValidator<ValidadorDadosClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest servletRequest;

	@Autowired
	private ClienteRepository clienteRepository;
	
	/*Esse metodo que verifica se a verificação é valida*/
	@Override
	public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext context) {

		Long uriId = obtemIdDaUri();
		List<FieldMessage> fieldMessages = new ArrayList<>();

		Cliente cliente = clienteRepository.findByEmail(clienteDTO.getEmail());
		if (cliente != null && !cliente.getId().equals(uriId)) {
			fieldMessages.add(new FieldMessage("email", "Email já existente"));
		}

		/* inclui os testes aqui, inserido erros na lista */
		for (FieldMessage fieldMessage : fieldMessages) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMensagem())
					.addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		}
		return fieldMessages.isEmpty();
	}

	private Long obtemIdDaUri() {
		@SuppressWarnings("unchecked")
		Map<String, String> servletRequests = (Map<String, String>) servletRequest
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		return Long.parseLong(servletRequests.get("id"));
	}

}
