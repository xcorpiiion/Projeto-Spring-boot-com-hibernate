package br.com.estudo.projetoweb.resources.exception;

import br.com.estudo.projetoweb.services.exception.AuthorizationException;
import br.com.estudo.projetoweb.services.exception.DataIntegratydException;
import br.com.estudo.projetoweb.services.exception.FileException;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	/*
	 * @ExceptionHandler -> ele captura erros lançados (Ele tem que ser o error que
	 * está no argumento) e permite com que eu possa fazer um tratamento de status
	 * apropriado para cada erro
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		/*Com o campo a baixo eu informei os detalhes do meu erro e informei qual é o tipo de error*/
		StandardError standardError = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				System.currentTimeMillis(), "Not found", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}

	@ExceptionHandler(DataIntegratydException.class)
	public ResponseEntity<StandardError> dataIntegratyd(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis(), "Integridade de dados", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError validationError = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(),
				System.currentTimeMillis(), "Erro de validação", request.getRequestURI());
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(),
				System.currentTimeMillis(), "Acesso negado", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standardError);
	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis(), "Erro de arquivo", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}

	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request) {
		/*
		 * O campo abaixo pega o codigo http que veio da exception da
		 * AmazonServiceException
		 */
		HttpStatus codigoHttp = HttpStatus.valueOf(e.getErrorCode());
		StandardError standardError = new StandardError(codigoHttp.value(), e.getMessage(), System.currentTimeMillis(),
				"Erro Amazon Service", request.getRequestURI());
		return ResponseEntity.status(codigoHttp).body(standardError);
	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis(), "Erro Amazon Client", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis(), "Erro Amazon S3", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}

}
