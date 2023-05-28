package com.gateway.Exception;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
	
	@ExceptionHandler(AuthenticationFailedException.class)
	@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
	public @ResponseBody ExceptionResponse getAuthenticationFailedException(final AuthenticationFailedException exception)
	{
		ExceptionResponse error = new ExceptionResponse();
		error.setResponsemsg(exception.getMessage());
		error.setRequestUrl("");

		return error;
	}
	
	@ExceptionHandler(ValueRequiredException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionResponse getValueRequiredException(final ValueRequiredException exception)
	{
		ExceptionResponse error = new ExceptionResponse();
		error.setResponsemsg(exception.getMessage());
		error.setRequestUrl("");

		return error;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse getException(final Exception exception)
	{
		ExceptionResponse error = new ExceptionResponse();
		error.setResponsemsg(exception.getMessage());
		error.setRequestUrl("");

		return error;
	}

}
