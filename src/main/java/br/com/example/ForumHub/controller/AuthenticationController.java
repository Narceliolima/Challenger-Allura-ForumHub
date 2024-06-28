package br.com.example.ForumHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.example.ForumHub.domain.user.User;
import br.com.example.ForumHub.domain.user.UserAuthenticationData;
import br.com.example.ForumHub.infra.security.JWTTokenData;
import br.com.example.ForumHub.infra.security.JWTTokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("login")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private JWTTokenService tokenService;
	
	@PostMapping
	public ResponseEntity<Object> login(@RequestBody @Valid UserAuthenticationData data){
	
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		Authentication authentication =  manager.authenticate(authenticationToken);
		String jwtToken = tokenService.generateToken((User)authentication.getPrincipal());
		
		return ResponseEntity.ok(new JWTTokenData(jwtToken));
	}
}
