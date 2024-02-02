package org.app.edufun.Configuration.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.app.edufun.service.UserMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTConfig {

	private String SECRET_KEY = "secret";
	
	@Autowired
	private UserMasterService masterService;
	
	public String extractUsername(String token) {
		try {
			return extractCLaim(token, Claims::getSubject);
		}
		catch (Exception e) {
			return null;
		}
	}

	public <T> Date extractExpiration(String token) {
		return extractCLaim(token, Claims::getExpiration);
	}

	public <T> T extractCLaim(String token, Function<Claims, T> claimResolver){
		final Claims claims = extractAllCLaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllCLaims(String token){
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		final Object dto  = masterService.getUser(userDetails.getUsername());
		Map<String, Object> claims = new HashMap<>();
		claims.put("data", dto);
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration((new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
