package br.com.example.ForumHub.infra.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandling {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handler404Error(){
		
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handler400Error(MethodArgumentNotValidException ex) {
		
		List<FieldError> errors = ex.getFieldErrors();
		
		return ResponseEntity.badRequest().body(errors.stream().map(ErrorDataValidation::new).collect(Collectors.toList()));
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handler400Error(HttpMessageNotReadableException ex) {
	    return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handlerBadCredentialError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Object> handlerAuthenticationError() {
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handlerNegateAcessError() {
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handler500Error(Exception ex) {
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
	}
	
	@ExceptionHandler(APIValidationException.class)
	public ResponseEntity<Object> handlerBusinessRuleError(APIValidationException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	private record ErrorDataValidation(String camp, String message) {
		public ErrorDataValidation(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
}
