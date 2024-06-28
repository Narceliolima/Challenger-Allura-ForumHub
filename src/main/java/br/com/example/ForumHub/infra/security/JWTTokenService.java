package br.com.example.ForumHub.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import br.com.example.ForumHub.domain.user.User;

@Service
public class JWTTokenService {

	private static final String ISSUER = "API ForumHub";
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(User user) {
		
	    Algorithm algorithm = Algorithm.HMAC256(secret);
	    
	    try {
	    	return JWT.create().withIssuer(ISSUER).withSubject(user.getLogin())
	    			.withClaim("id", user.getId()).withClaim("test", "galactic shutdown")
		    		.withExpiresAt(generateExpirationTime()).sign(algorithm);
		} catch (IllegalArgumentException|JWTCreationException e) {
			throw new RuntimeException("Erro ao gerar Token JWT", e);
		}
	}
	
	public String getSubject(String jwtToken) {
		
		Algorithm algorithm = Algorithm.HMAC256(secret);
		
		try {
			return JWT.require(algorithm).withIssuer(ISSUER).build()
					.verify(jwtToken).getSubject();
		} catch (Exception e) {
			throw new RuntimeException("Erro, Token inválido ou expirado", e);
		}
	}
	
	private Instant generateExpirationTime() {
		
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
