package br.com.estudo.projetoweb.services.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*@Constraint -> ele pergunta qual vai ser a minha classe de validação (que vai implementar a interface)*/
@Constraint(validatedBy = ClienteUpdateValidator.class)
/*
 * @Target -> Ele fala em que local eu posso colocar essa anotação, ou seja, na
 * classe ou em algum atributo
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
/* Cria uma anotação personalizada */
public @interface ValidadorDadosClienteUpdate {

	/*
	 * Cria uma mensagem padrão para a anotação mas ele pode ser alterado via
	 * parametro
	 */
	String message() default "Erro de validação";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
