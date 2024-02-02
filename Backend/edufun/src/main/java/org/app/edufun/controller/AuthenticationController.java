package org.app.edufun.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.app.edufun.dao.UserRepository;
import org.app.edufun.entity.User;
import org.app.edufun.service_impl.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
/*import org.springframework.web.bind.annotation.CrossOrigin;*/
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.app.edufun.Configuration.filters.MSRequestFilter;
import org.app.edufun.Configuration.jwt.JWTConfig;
import org.app.edufun.dto.AuthenticationRequest;
import org.app.edufun.dto.AuthenticationResponse;
import org.app.edufun.dto.ResponseDto;
import org.app.edufun.service.UserMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/EduFun/login")
@Api(value = "Login system", description = "Login Management Operation", tags = "Login Controller")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private MSRequestFilter msRequestFilter;

	@Autowired
	private JWTConfig jWTConfig;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/welcome")
	public String welcomeWorld() {
		return "Welcome to Edu&Fun !";
	}

	@PostMapping("/doAnyLogin")
	public ResponseEntity<AuthenticationResponse> doLogin(HttpServletRequest request,
														  @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		HttpSession session = request.getSession();
		String email = authenticationRequest.getEmail();
		String password = bCryptPasswordEncoder.encode(authenticationRequest.getPassword());

		if ((email == null || email.isEmpty()) && (password == null || password.isEmpty())) {
			return new ResponseEntity<AuthenticationResponse>(
					new AuthenticationResponse(null, false, "Username and Password are mandatory !", new ArrayList<Object>()),
					HttpStatus.BAD_REQUEST);
		} else if (email == null || email.isEmpty()) {
			return new ResponseEntity<AuthenticationResponse>(
					new AuthenticationResponse(null, false, "Username is a mandatory !", new ArrayList<Object>()),
					HttpStatus.BAD_REQUEST);
		} else if (password == null || password.isEmpty()) {
			return new ResponseEntity<AuthenticationResponse>(
					new AuthenticationResponse(null, false, "Password is a mandatory !", new ArrayList<Object>()),
					HttpStatus.BAD_REQUEST);
		}
		if (!userRepository.existByEmail(email)) {
			return new ResponseEntity<AuthenticationResponse>(
					new AuthenticationResponse(null, false, "Incorrect username", new ArrayList<Object>()),
					HttpStatus.BAD_REQUEST);
		} else {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
			} catch (BadCredentialsException e) {
				return new ResponseEntity<AuthenticationResponse>(
						new AuthenticationResponse(null, false, "Incorrect password.", new ArrayList<Object>()),
						HttpStatus.OK);
			}
		}

		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String jwtToken = jWTConfig.generateToken(userDetails);
		final String validateToken = msRequestFilter.getAllTokenData(session, jwtToken);
		System.out.println(validateToken);

		User user = userRepository.searchByEmail(authenticationRequest.getEmail());

		return new ResponseEntity<AuthenticationResponse>(
				new AuthenticationResponse(jwtToken, true, "Congrats ! you have login successfully.", user),
				HttpStatus.OK);
	}

}

