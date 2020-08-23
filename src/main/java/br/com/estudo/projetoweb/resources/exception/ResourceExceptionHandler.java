package br.com.estudo.projetoweb.resources.exception;

import br.com.estudo.projetoweb.services.exception.AuthorizationException;
import br.com.estudo.projetoweb.services.exception.DataIntegratydException;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError standardError = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(DataIntegratydException.class)
    public ResponseEntity<StandardError> dataIntegratyd(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> dataIntegratyd(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        for(FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
    }
    
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
        StandardError standardError = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standardError);
    }

}
