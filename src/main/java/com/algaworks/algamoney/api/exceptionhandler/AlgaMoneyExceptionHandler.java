package com.algaworks.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class AlgaMoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String userMessage = messageSource.getMessage("mensagem.usuario", null, LocaleContextHolder.getLocale());
		String stacktrace = ex.getLocalizedMessage();
		List<Error> errorList = Arrays.asList(new Error(userMessage, stacktrace));
		return super.handleExceptionInternal(ex, errorList, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Error> errorList = generateErrorList(ex.getBindingResult());
		return super.handleExceptionInternal(ex, errorList, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler
	private ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String userMessage = messageSource.getMessage("resource.not_found", null, LocaleContextHolder.getLocale());
		String stacktrace = ex.getLocalizedMessage();
		List<Error> errorList = Arrays.asList(new Error(userMessage, stacktrace));
		return super.handleExceptionInternal(ex, errorList, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	public List<Error> generateErrorList(BindingResult bindingResult){
		ArrayList<Error> errors = new ArrayList<Error>();
		
		for (FieldError error : bindingResult.getFieldErrors()) {
			errors.add(new Error(messageSource.getMessage(error, LocaleContextHolder.getLocale()), error.toString()));
		}
		return errors;
	}
	
	public static class Error {
		private String userMessage;
		private String stacktrace;

		public Error(String userMessage, String stacktrace) {
			super();
			this.userMessage = userMessage;
			this.stacktrace = stacktrace;
		}

		public String getUserMessage() {
			return userMessage;
		}

		public String getStacktrace() {
			return stacktrace;
		}
	}
}
