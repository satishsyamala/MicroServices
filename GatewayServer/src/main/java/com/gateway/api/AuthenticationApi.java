package com.gateway.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gateway.Exception.AuthenticationFailedException;
import com.gateway.Exception.ValueRequiredException;
import com.gateway.config.JwtUtil;
import com.gateway.dao.UserRepository;
import com.gateway.pojos.Users;
import com.gateway.util.AESEncryption;

import net.bytebuddy.implementation.bytecode.Throw;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationApi {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> loginUsers(@RequestBody Users users)
			throws AuthenticationFailedException,ValueRequiredException,Exception {
		Users user = null;
		Optional<String> userName = Optional.ofNullable(users.getMobileNo());
		Optional<String> password = Optional.ofNullable(users.getPassword());
		if (userName.isPresent() && password.isPresent()) {
			AESEncryption.init();
			String pas = AESEncryption.getInstance().encode(password.get());
			user = userRepository.findByMobileNoAndPassword(userName.get(), pas);
			if (user != null) {
                user.setToken(jwtUtil.generateToken(users.getMobileNo()));
				
			} else
				throw new AuthenticationFailedException("invalid credentials");
		} else
			throw new ValueRequiredException(userName.isPresent() ? "Password Required" : "Moblie Number requied");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
