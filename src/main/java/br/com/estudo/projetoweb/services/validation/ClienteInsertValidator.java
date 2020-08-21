package br.com.estudo.projetoweb.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.domain.enums.EnumTipoCliente;
import br.com.estudo.projetoweb.dto.ClienteNewDTO;
import br.com.estudo.projetoweb.repositories.ClienteRepository;
import br.com.estudo.projetoweb.resources.exception.FieldMessage;
import br.com.estudo.projetoweb.services.validation.utils.CpfOrCnpjValidation;

public class ClienteInsertValidator implements ConstraintValidator<ValidadorDadosClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
		List<FieldMessage> fieldMessages = new ArrayList<>();
		
		if(clienteNewDTO.getTipo().equals(EnumTipoCliente.PESSOAFISICA.getCodigo()) && !CpfOrCnpjValidation.isValidSsn(clienteNewDTO.getCpfOuCnpj())) {
			fieldMessages.add(new FieldMessage("cpfOuCnpj", "CPF invalido"));
		}
		if(clienteNewDTO.getTipo().equals(EnumTipoCliente.PESSOAJURIDICA.getCodigo()) && !CpfOrCnpjValidation.isValidTfn(clienteNewDTO.getCpfOuCnpj())) {
			fieldMessages.add(new FieldMessage("cpfOuCnpj", "CNPJ invalido"));
		}
		
		Cliente cliente = clienteRepository.findByEmail(clienteNewDTO.getEmail());
		if(cliente != null) {
			fieldMessages.add(new FieldMessage("email", "Email já existente"));
		}
		
		// inclui os testes aqui, inserido erros na lista
		for (FieldMessage fieldMessage : fieldMessages) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMensagem())
					.addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		}
		return fieldMessages.isEmpty();
	}

}
