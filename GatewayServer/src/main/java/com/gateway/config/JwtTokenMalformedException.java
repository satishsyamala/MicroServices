package com.gateway.config;

import javax.naming.AuthenticationException;


public class JwtTokenMalformedException extends AuthenticationException  {

	public JwtTokenMalformedException(String message) {
		super(message);
	}

}
