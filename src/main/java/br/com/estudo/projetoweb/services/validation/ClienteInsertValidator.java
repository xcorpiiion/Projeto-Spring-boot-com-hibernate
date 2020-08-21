package br.com.estudo.projetoweb.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.estudo.projetoweb.domain.enums.EnumTipoCliente;
import br.com.estudo.projetoweb.dto.ClienteNewDTO;
import br.com.estudo.projetoweb.resources.exception.FieldMessage;
import br.com.estudo.projetoweb.services.validation.utils.CpfOrCnpjValidation;

public class ClienteInsertValidator implements ConstraintValidator<ValidadorCnpjAndCpf, ClienteNewDTO> {

	@Override
	public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
		List<FieldMessage> fieldMessages = new ArrayList<>();
		
		if(clienteNewDTO.getTipo().equals(EnumTipoCliente.PESSOAFISICA.getCodigo()) && !CpfOrCnpjValidation.isValidSsn(clienteNewDTO.getCpfOuCnpj())) {
			fieldMessages.add(new FieldMessage("cpfOuCnpj", "CPF invalido"));
		}
		if(clienteNewDTO.getTipo().equals(EnumTipoCliente.PESSOAJURIDICA.getCodigo()) && !CpfOrCnpjValidation.isValidTfn(clienteNewDTO.getCpfOuCnpj())) {
			fieldMessages.add(new FieldMessage("cpfOuCnpj", "CNPJ invalido"));
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
