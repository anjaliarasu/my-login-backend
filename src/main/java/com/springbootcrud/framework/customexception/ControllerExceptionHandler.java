package com.springbootcrud.framework.customexception;

import java.util.Locale;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springbootcrud.model.wrapper.Message;
import com.springbootcrud.model.wrapper.MessageType;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * The Class ControllerExceptionHandler.
 */
@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

	/** The message source. */
	@Autowired
	private MessageSource messageSource;


	/** The logger. */
	private Logger logger = LogManager.getLogger(ControllerExceptionHandler.class.getName());

	/** The Constant VALIDATION_ERROR. */
	private static final String VALIDATION_ERROR = "Validation Error";

	/**
	 * Instantiates a new controller exception handler.
	 *
	 * @param messageSource the message source
	 */
	@Autowired
	public ControllerExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Handle validation failure.
	 *
	 * @param request                      the request
	 * @param constarintViolationException the constarint violation exception
	 * @return the message
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Message handleValidationFailure(HttpServletRequest request,
			ConstraintViolationException constarintViolationException) {

		StringBuilder messages = new StringBuilder();
		String message = "";

		if (constarintViolationException != null) {
			for (ConstraintViolation<?> violation : constarintViolationException.getConstraintViolations()) {
				Locale currentLocale = LocaleContextHolder.getLocale();
				messages.append(messageSource.getMessage(violation.getMessage(), null, currentLocale) + ", ");
			}

			if (messages.lastIndexOf(",") >= 0) {
				message = messages.toString().substring(0, messages.lastIndexOf(","));
			}

		}

		logger.error("", constarintViolationException);
		return ConstructMessage(MessageType.ERROR, message, System.currentTimeMillis(),
				String.valueOf(HttpStatus.BAD_REQUEST.value()), VALIDATION_ERROR,
				ConstraintViolationException.class.getName(),
				request.getRequestURI().substring(request.getContextPath().length()), constarintViolationException);
	}

	/**
	 * Handle validation failure.
	 *
	 * @param request                         the request
	 * @param dataIntegrityViolationException the data integrity violation exception
	 * @return the message
	 */
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public Message handleValidationFailure(HttpServletRequest request,
			DataIntegrityViolationException dataIntegrityViolationException) {

		StringBuilder messages = new StringBuilder();

		if (dataIntegrityViolationException != null) {
			Locale currentLocale = LocaleContextHolder.getLocale();
			messages.append(messageSource.getMessage(dataIntegrityViolationException.getMessage(), null,
					dataIntegrityViolationException.getMessage(), currentLocale));
		}

		logger.error("", dataIntegrityViolationException);
		return ConstructMessage(MessageType.ERROR, messages.toString(), System.currentTimeMillis(),
				String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()), VALIDATION_ERROR,
				DataIntegrityViolationException.class.getName(),
				request.getRequestURI().substring(request.getContextPath().length()), dataIntegrityViolationException);
	}

	/**
	 * Handle validation failure.
	 *
	 * @param request               the request
	 * @param accessDeniedException the access denied exception
	 * @return the message
	 */
	
	  @ExceptionHandler(value = { AccessDeniedException.class })
	  
	  @ResponseStatus(value = HttpStatus.FORBIDDEN) public Message
	  handleValidationFailure(HttpServletRequest request, AccessDeniedException
	  accessDeniedException) {
	  
	  StringBuilder messages = new StringBuilder();
	  
	  if (accessDeniedException != null) { Locale currentLocale =
	  LocaleContextHolder.getLocale();
	  messages.append(messageSource.getMessage(accessDeniedException.getMessage(),
	  null, accessDeniedException.getMessage(), currentLocale)); }
	  
	  logger.error("", accessDeniedException); return
	  ConstructMessage(MessageType.ERROR, messages.toString(),
	  System.currentTimeMillis(), String.valueOf(HttpStatus.FORBIDDEN.value()),
	  "Unauthorize Permission", AccessDeniedException.class.getName(),
	  request.getRequestURI().substring(request.getContextPath().length()),
	  accessDeniedException); }
	 
	/**
	 * Handle validation failure.
	 *
	 * @param request                the request
	 * @param noSuchElementException the no such element exception
	 * @return the message
	 */
	@ExceptionHandler(value = { NoSuchElementException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public Message handleValidationFailure(HttpServletRequest request, NoSuchElementException noSuchElementException) {

		StringBuilder messages = new StringBuilder();

		if (noSuchElementException != null) {
			Locale currentLocale = LocaleContextHolder.getLocale();
			messages.append(messageSource.getMessage(noSuchElementException.getMessage(), null,
					noSuchElementException.getMessage(), currentLocale));
		}

		logger.error("", noSuchElementException);
		return ConstructMessage(MessageType.ERROR, messages.toString(), System.currentTimeMillis(),
				String.valueOf(HttpStatus.NOT_FOUND.value()), "Resource not found",
				NoSuchElementException.class.getName(),
				request.getRequestURI().substring(request.getContextPath().length()), noSuchElementException);
	}

	/**
	 * Handle method argument not valid.
	 *
	 * @param request                         the request
	 * @param methodArgumentNotValidException the method argument not valid
	 *                                        exception
	 * @return the message
	 */
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Message handleMethodArgumentNotValid(HttpServletRequest request,
			MethodArgumentNotValidException methodArgumentNotValidException) {

		BindingResult result = methodArgumentNotValidException.getBindingResult();

		StringBuilder messages = new StringBuilder();

		
		  if (result.getFieldError() != null) { 
			  Locale currentLocale =
		  LocaleContextHolder.getLocale();
		  messages.append(messageSource.getMessage(methodArgumentNotValidException.getMessage(), null,
				  result.getFieldError().getDefaultMessage(), currentLocale)); }
		 
		
		logger.error("", methodArgumentNotValidException);
		return ConstructMessage(MessageType.ERROR, messages.toString(), System.currentTimeMillis(),
				String.valueOf(HttpStatus.BAD_REQUEST.value()), "Validation Argument Error",
				MethodArgumentNotValidException.class.getName(),
				request.getRequestURI().substring(request.getContextPath().length()), methodArgumentNotValidException);
	}

	
	/**
	 * Error.
	 *
	 * @param request   the request
	 * @param exception the exception
	 * @return the message
	 */
	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Message error(HttpServletRequest request, Exception exception) {
		logger.error("", exception);
		return ConstructMessage(MessageType.ERROR, exception.getMessage(), System.currentTimeMillis(),
				String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "Internal Server Error",
				exception.getClass().getCanonicalName(),
				request.getRequestURI().substring(request.getContextPath().length()), exception);
	}
	
	/**
	 * Construct message.
	 *
	 * @param messageType        the message type
	 * @param message            the message
	 * @param timestamp          the timestamp
	 * @param status             the status
	 * @param error              the error
	 * @param exceptionClassName the exception class name
	 * @param path               the path
	 * @param exception          the exceptiona
	 * @return the message
	 */
	private Message ConstructMessage(MessageType messageType, String message, long timestamp, String status,
			String error, String exceptionClassName, String path, Exception exception) {
		
		  return new Message(messageType, message, timestamp, status, error,
		  exceptionClassName, path);
		 
	}

}
